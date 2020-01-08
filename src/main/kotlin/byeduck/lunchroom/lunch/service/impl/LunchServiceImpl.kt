package byeduck.lunchroom.lunch.service.impl

import byeduck.lunchroom.domain.*
import byeduck.lunchroom.error.exceptions.AlreadyVotedException
import byeduck.lunchroom.error.exceptions.InvalidPhaseException
import byeduck.lunchroom.error.exceptions.InvalidRoomException
import byeduck.lunchroom.error.exceptions.NotEnoughPointsAvailableException
import byeduck.lunchroom.lunch.exceptions.LunchProposalNotFoundException
import byeduck.lunchroom.lunch.service.LunchService
import byeduck.lunchroom.repositories.LunchRepository
import byeduck.lunchroom.repositories.RoomsRepository
import byeduck.lunchroom.repositories.UsersRepository
import byeduck.lunchroom.room.exceptions.RoomNotFoundException
import byeduck.lunchroom.user.exceptions.UserNotFoundException
import org.springframework.beans.factory.annotation.Autowired
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
        private val roomsRepository: RoomsRepository
) : LunchService {

    override fun addLunchProposal(userId: String, roomId: String, title: String, menuItems: List<MenuItem>): LunchProposal {
        val room = roomsRepository.findById(roomId).orElseThrow { RoomNotFoundException(roomId) }
        if (!isPostPhase(room)) {
            throw InvalidPhaseException()
        }
        val user = usersRepository.findById(userId).orElseThrow { UserNotFoundException(userId) }
        validateUserInRoom(user, room)
        return lunchRepository.insert(LunchProposal(roomId, title, menuItems))
    }

    override fun voteForProposal(userId: String, roomId: String, proposalId: String, rating: Int): LunchProposal {
        val room = roomsRepository.findById(roomId).orElseThrow { RoomNotFoundException(roomId) }
        if (!isVotePhase(room)) {
            throw InvalidPhaseException()
        }
        val user = usersRepository.findById(userId).orElseThrow { UserNotFoundException(userId) }
        validateUserInRoom(user, room)
        val roomUserIndex = room.users.indexOf(RoomUser(user))
        val lunchProposal = lunchRepository.findById(proposalId)
        return lunchProposal.map {
            if (room.users[roomUserIndex].votes.contains(Vote(it.id!!))) {
                throw AlreadyVotedException()
            }
            validateUserPoints(rating, room.users[roomUserIndex])
            room.users[roomUserIndex].votes.add(Vote(it.id!!, rating))
            room.users[roomUserIndex].points -= rating
            roomsRepository.save(room)
            usersRepository.save(user)
            lunchRepository.save(it.voteFor(rating))
        }.orElseThrow { LunchProposalNotFoundException(proposalId) }
    }

    override fun findAllByRoomId(userId: String, roomId: String): List<LunchProposal> {
        val room = roomsRepository.findById(roomId).orElseThrow { RoomNotFoundException(roomId) }
        val user = usersRepository.findById(userId).orElseThrow { UserNotFoundException(userId) }
        validateUserInRoom(user, room)
        return lunchRepository.findAllByRoomId(roomId)
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
        return room.postDeadline < now && room.voteDeadline > now
    }

    private fun isPostPhase(room: Room): Boolean {
        val now = System.currentTimeMillis()
        return room.signDeadline < now && room.postDeadline > now
    }

}