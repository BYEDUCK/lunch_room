package byeduck.lunchroom.room.service

import byeduck.lunchroom.domain.Room
import byeduck.lunchroom.repositories.RoomsRepository
import byeduck.lunchroom.repositories.UsersRepository
import byeduck.lunchroom.room.exceptions.RoomAlreadyExistsException
import byeduck.lunchroom.room.exceptions.RoomNotFoundException
import byeduck.lunchroom.user.exceptions.UserNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RoomServiceImpl(
        @Autowired
        private val roomsRepository: RoomsRepository,
        @Autowired
        private val usersRepository: UsersRepository
) : RoomService {

    override fun addRoom(name: String, ownerId: String, deadlines: Deadlines): Room {
        val found = roomsRepository.findByName(name)
        if (found.isPresent) {
            throw RoomAlreadyExistsException()
        }
        val owner = usersRepository.findById(ownerId)
        return owner.map {
            val room = Room(name, ownerId, deadlines.signDeadline, deadlines.postDeadline, deadlines.voteDeadline)
            val insertedRoom = roomsRepository.insert(room)
            it.rooms.add(insertedRoom.id!!)
            insertedRoom.users.add(it)
            usersRepository.save(it)
            return@map roomsRepository.save(insertedRoom)
        }.orElseThrow { UserNotFoundException() }
    }

    override fun findRoomsByUserId(userId: String): MutableList<Room> {
        val user = usersRepository.findById(userId)
        return user.map {
            return@map roomsRepository.findAllById(it.rooms).toMutableList()
        }.orElseThrow { UserNotFoundException() }
    }

    override fun joinRoom(name: String, userId: String): Room {
        val user = usersRepository.findById(userId)
        if (!user.isPresent) {
            throw UserNotFoundException()
        }
        val room = roomsRepository.findByName(name)
        if (!room.isPresent) {
            throw RoomNotFoundException()
        }
        if (room.get().users.contains(user.get())) {
            return room.get()
        }
        room.get().users.add(user.get())
        return roomsRepository.save(room.get())
    }
}