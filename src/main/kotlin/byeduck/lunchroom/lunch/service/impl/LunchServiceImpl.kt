package byeduck.lunchroom.lunch.service.impl

import byeduck.lunchroom.domain.*
import byeduck.lunchroom.error.exceptions.*
import byeduck.lunchroom.lunch.controller.response.LunchProposalDTO
import byeduck.lunchroom.lunch.controller.response.LunchResponse
import byeduck.lunchroom.lunch.exceptions.InvalidProposalException
import byeduck.lunchroom.lunch.exceptions.LunchProposalNotFoundException
import byeduck.lunchroom.lunch.service.LunchService
import byeduck.lunchroom.repositories.LunchRepository
import byeduck.lunchroom.repositories.RoomsRepository
import byeduck.lunchroom.repositories.UsersRepository
import byeduck.lunchroom.room.exceptions.RoomNotFoundException
import byeduck.lunchroom.user.exceptions.UserNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Service
@Validated
class LunchServiceImpl(
        @Autowired
        private val lunchRepository: LunchRepository,
        @Autowired
        private val usersRepository: UsersRepository,
        @Autowired
        private val roomsRepository: RoomsRepository,
        @Autowired
        private val msgTemplate: SimpMessagingTemplate
) : LunchService {

    override fun addLunchProposal(
            userId: String, roomId: String, title: String, menuUrl: String, menuItems: List<MenuItem>
    ): LunchProposal {
        val room = roomsRepository.findById(roomId).orElseThrow { RoomNotFoundException(roomId) }
        if (!isInitialPhase(room)) {
            throw InvalidPhaseException()
        }
        val user = usersRepository.findById(userId).orElseThrow { UserNotFoundException(userId) }
        validateUserInRoom(user, room)
        return lunchRepository.insert(LunchProposal(roomId, title, menuUrl, menuItems, userId))
    }

    override fun voteForProposal(userId: String, roomId: String, proposalId: String, rating: Int): LunchProposal {
        val room = roomsRepository.findById(roomId).orElseThrow { RoomNotFoundException(roomId) }
        if (!isVotePhase(room)) {
            throw InvalidPhaseException()
        }
        if (!room.open) {
            throw RoomClosedException()
        }
        val user = usersRepository.findById(userId).orElseThrow { UserNotFoundException(userId) }
        validateUserInRoom(user, room)
        val roomUserIndex = room.users.indexOf(RoomUser(user))
        val lunchProposal = lunchRepository.findById(proposalId)
        return lunchProposal.map {
            validateUserPoints(rating, room.users[roomUserIndex])
            val voteIndex = room.users[roomUserIndex].votes.indexOf(Vote(it.id!!))
            val savedProposal: LunchProposal
            if (voteIndex >= 0) {
                val oldRating = room.users[roomUserIndex].votes[voteIndex].rating
                room.users[roomUserIndex].points += oldRating
                room.users[roomUserIndex].points -= rating
                room.users[roomUserIndex].votes[voteIndex].rating = rating
                savedProposal = lunchRepository.save(it.revote(oldRating, rating))
            } else {
                room.users[roomUserIndex].votes.add(Vote(it.id!!, rating))
                room.users[roomUserIndex].points -= rating
                savedProposal = lunchRepository.save(it.voteFor(rating))
            }
            roomsRepository.save(room)
            usersRepository.save(user)
            return@map savedProposal
        }.orElseThrow { LunchProposalNotFoundException(proposalId) }
    }

    override fun findAllByRoomId(userId: String, roomId: String): List<LunchProposal> {
        val room = roomsRepository.findById(roomId).orElseThrow { RoomNotFoundException(roomId) }
        val user = usersRepository.findById(userId).orElseThrow { UserNotFoundException(userId) }
        validateUserInRoom(user, room)
        return lunchRepository.findAllByRoomId(roomId)
    }

    override fun deleteProposal(userId: String, roomId: String, proposalId: String) {
        val user = usersRepository.findById(userId).orElseThrow { UserNotFoundException(userId) }
        val room = roomsRepository.findById(roomId).orElseThrow { RoomNotFoundException(roomId) }
        validateUserInRoom(user, room)
        val proposal = lunchRepository.findById(proposalId).orElseThrow { LunchProposalNotFoundException(proposalId) }
        validateProposalInRoom(proposal, room)
        if (isUserEligibleToModifyProposal(userId, proposal, room)) {
            lunchRepository.delete(proposal)
            room.users.forEach {
                it.votes.remove(Vote(proposalId))
            }
            roomsRepository.save(room)
        } else {
            throw UnauthorizedException()
        }
    }

    override fun editProposal(
            userId: String, roomId: String, proposalId: String, title: String, menuUrl: String, menuItems: List<MenuItem>
    ): LunchProposal {
        val user = usersRepository.findById(userId).orElseThrow { UserNotFoundException(userId) }
        val room = roomsRepository.findById(roomId).orElseThrow { RoomNotFoundException(roomId) }
        validateUserInRoom(user, room)
        val proposal = lunchRepository.findById(proposalId).orElseThrow { LunchProposalNotFoundException(proposalId) }
        validateProposalInRoom(proposal, room)
        if (isUserEligibleToModifyProposal(userId, proposal, room)) {
            return lunchRepository.save(LunchProposal(
                    proposalId, roomId, title, menuUrl, menuItems,
                    proposal.proposalOwnerId, proposal.ratingSum, proposal.votesCount
            ))
        } else {
            throw UnauthorizedException()
        }
    }

    override fun getProposalCount(roomId: String): Int {
        return lunchRepository.countByRoomId(roomId)
    }

    override fun notifyRoomUsersAboutLunchProposalsChange(roomId: String, updated: List<LunchProposal>) {
        val total = getProposalCount(roomId)
        msgTemplate.convertAndSend("/room/proposals/$roomId", LunchResponse(total, updated.map {
            LunchProposalDTO.fromLunchProposal(it)
        }))
    }

    private fun validateProposalInRoom(proposal: LunchProposal, room: Room) {
        if (proposal.roomId != room.id) {
            throw InvalidProposalException(proposal.id!!)
        }
    }

    private fun isUserEligibleToModifyProposal(userId: String, proposal: LunchProposal, room: Room): Boolean {
        return userId == room.owner || (userId == proposal.proposalOwnerId && isInitialPhase(room))
    }

    private fun validateUserInRoom(user: User, room: Room) {
        if (!user.rooms.contains(room.id)) {
            throw InvalidRoomException()
        }
    }

    private fun validateUserPoints(rating: Int, roomUser: RoomUser) {
        if (roomUser.points < rating) {
            throw NotEnoughPointsAvailableException()
        }
    }

    private fun isVotePhase(room: Room): Boolean {
        val now = System.currentTimeMillis()
        return room.initialDeadline < now && room.voteDeadline > now
    }

    private fun isInitialPhase(room: Room): Boolean {
        val now = System.currentTimeMillis()
        return room.initialDeadline > now
    }

}