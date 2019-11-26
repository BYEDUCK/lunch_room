package byeduck.lunchroom.room.service

import byeduck.lunchroom.domain.Room
import byeduck.lunchroom.error.exceptions.JoiningPastDeadlineException
import byeduck.lunchroom.error.exceptions.UnauthorizedException
import byeduck.lunchroom.repositories.RoomsRepository
import byeduck.lunchroom.repositories.UsersRepository
import byeduck.lunchroom.room.exceptions.RoomAlreadyExistsException
import byeduck.lunchroom.room.exceptions.RoomNotFoundException
import byeduck.lunchroom.token.service.TokenService
import byeduck.lunchroom.user.exceptions.UserNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class RoomServiceImpl(
        @Autowired
        private val roomsRepository: RoomsRepository,
        @Autowired
        private val usersRepository: UsersRepository,
        @Autowired
        private val tokenService: TokenService
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
            insertedRoom.users.add(it)
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

    override fun joinRoom(name: String, userId: String): Room {
        val room = roomsRepository.findByName(name)
                .orElseThrow { RoomNotFoundException(name) }
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
                room.users.add(it)
                return@map roomsRepository.save(room)
            }
        }.orElseThrow { UserNotFoundException(userId) }
    }

    override fun deleteRoom(id: String, token: String) {
        val room = roomsRepository.findById(id)
                .orElseThrow { throw RoomNotFoundException(id) }
        validateRoomOwnership(room, token)
        room.users.forEach {
            it.rooms.remove(room.id)
            usersRepository.save(it)
        }
        roomsRepository.delete(room)
    }

    override fun updateRoom(name: String, token: String, newDeadlines: Deadlines): Room {
        val room = roomsRepository.findByName(name)
                .orElseThrow { throw RoomNotFoundException(name) }
        validateRoomOwnership(room, token)
        room.signDeadline = newDeadlines.signDeadline
        room.postDeadline = newDeadlines.postDeadline
        room.voteDeadline = newDeadlines.voteDeadline
        return roomsRepository.save(room)
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