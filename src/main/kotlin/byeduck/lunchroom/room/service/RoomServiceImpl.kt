package byeduck.lunchroom.room.service

import byeduck.lunchroom.domain.Lottery
import byeduck.lunchroom.domain.Room
import byeduck.lunchroom.domain.RoomUser
import byeduck.lunchroom.error.exceptions.*
import byeduck.lunchroom.repositories.LotteryRepository
import byeduck.lunchroom.repositories.LunchRepository
import byeduck.lunchroom.repositories.RoomsRepository
import byeduck.lunchroom.repositories.UsersRepository
import byeduck.lunchroom.room.exceptions.RoomAlreadyExistsException
import byeduck.lunchroom.room.exceptions.RoomNotFoundException
import byeduck.lunchroom.token.service.TokenService
import byeduck.lunchroom.user.exceptions.UserNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

@Service
class RoomServiceImpl(
        @Autowired
        private val roomsRepository: RoomsRepository,
        @Autowired
        private val usersRepository: UsersRepository,
        @Autowired
        private val lunchRepository: LunchRepository,
        @Autowired
        private val lotteryRepository: LotteryRepository,
        @Autowired
        private val tokenService: TokenService,
        @Value("\${user.start.points}")
        private val roomUserStartingPoints: Int
) : RoomService {

    override fun addRoom(name: String, ownerId: String, deadlines: Deadlines): Room {
        val found = roomsRepository.findByName(name)
        if (found.isPresent) {
            throw RoomAlreadyExistsException(found.get().name)
        }
        val owner = usersRepository.findById(ownerId)
        return owner.map {
            val room = Room(name, ownerId, deadlines.signDeadline, deadlines.postDeadline, deadlines.voteDeadline)
            val insertedRoom = roomsRepository.insert(room)
            it.rooms.add(insertedRoom.id!!)
            insertedRoom.users.add(RoomUser(it, roomUserStartingPoints))
            usersRepository.save(it)
            return@map roomsRepository.save(insertedRoom)
        }.orElseThrow { UserNotFoundException(ownerId) }
    }

    override fun findRoomsByUserId(userId: String): MutableList<Room> {
        val user = usersRepository.findById(userId)
        return user.map {
            roomsRepository.findAllById(it.rooms).toMutableList()
        }.orElseThrow { UserNotFoundException(userId) }
    }

    override fun findRoomByName(name: String): Room {
        return roomsRepository.findByName(name).orElseThrow { RoomNotFoundException(name) }
    }

    override fun joinRoomById(roomId: String, userId: String): Room {
        val room = roomsRepository.findById(roomId)
                .orElseThrow { RoomNotFoundException(roomId) }
        return joinRoom(room, userId)
    }

    override fun deleteRoom(id: String, token: String) {
        val room = roomsRepository.findById(id)
                .orElseThrow { throw RoomNotFoundException(id) }
        validateRoomOwnership(room, token)
        lunchRepository.findAllByRoomId(room.id!!).forEach {
            lunchRepository.delete(it)
        }
        room.users.forEach {
            it.user.rooms.remove(room.id!!)
            usersRepository.save(it.user)
        }
        roomsRepository.delete(room)
    }

    override fun updateRoom(roomId: String, token: String, newDeadlines: Deadlines): Room {
        val room = roomsRepository.findById(roomId)
                .orElseThrow { throw RoomNotFoundException(roomId) }
        validateRoomOwnership(room, token)
        if (room.voteDeadline > System.currentTimeMillis()) {
            throw UpdatingRoomWhileVotingException(Date(room.voteDeadline).toString())
        }
        room.signDeadline = newDeadlines.signDeadline
        room.postDeadline = newDeadlines.postDeadline
        room.voteDeadline = newDeadlines.voteDeadline
        room.users.forEach {
            it.points = roomUserStartingPoints
            it.votes = ArrayList()
        }
        lunchRepository.findAllByRoomId(roomId).forEach {
            it.ratingSum = 0
            it.votesCount = 0
            lunchRepository.save(it)
        }
        return roomsRepository.save(room)
    }

    override fun doTheLottery(userId: String, roomId: String, token: String): Lottery {
        val room = roomsRepository.findById(roomId).orElseThrow { RoomNotFoundException(roomId) }
        validateRoomOwnership(room, token)
        val lunchProposals = lunchRepository.findAllByRoomId(roomId)
        if (lunchProposals.size < 2) {
            throw OneProposalException()
        }
        val winnerProposal = lunchProposals.maxBy { it.ratingSum } ?: throw RuntimeException("Unexpected error")
        val usersCount = room.users.size
        if (usersCount < 2) {
            throw OnePersonRoomException()
        }
        val winnerUserIdx = Random.nextInt(0, usersCount)
        return lotteryRepository.save(
                Lottery(
                        room.users[winnerUserIdx].user.id!!, room.users[winnerUserIdx].user.nick,
                        roomId, winnerProposal.id!!
                )
        )
    }

    private fun joinRoom(room: Room, userId: String): Room {
        val user = usersRepository.findById(userId)
        return user.map {
            if (room.signDeadline < System.currentTimeMillis()) {
                throw JoiningPastDeadlineException(Date(room.signDeadline).toString())
            }
            if (user.get().rooms.contains(room.id)) {
                return@map room
            } else {
                it.rooms.add(room.id!!)
                usersRepository.save(it)
                room.users.add(RoomUser(it, roomUserStartingPoints))
                return@map roomsRepository.save(room)
            }
        }.orElseThrow { UserNotFoundException(userId) }
    }

    private fun validateRoomOwnership(room: Room, userToken: String) {
        val userNick = tokenService.getSubject(userToken)
        val user = usersRepository.findByNick(userNick)
                .orElseThrow { UserNotFoundException(userNick) }
        if (user.id!! != room.owner) {
            throw UnauthorizedException()
        }
    }
}