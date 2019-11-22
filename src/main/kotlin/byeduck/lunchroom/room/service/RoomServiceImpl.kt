package byeduck.lunchroom.room.service

import byeduck.lunchroom.domain.Room
import byeduck.lunchroom.repositories.RoomsRepository
import byeduck.lunchroom.repositories.UsersRepository
import byeduck.lunchroom.room.exceptions.RoomAlreadyExistsException
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
            val savedRoom = roomsRepository.insert(Room(name, ownerId, deadlines.signDeadline, deadlines.postDeadline, deadlines.priorityDeadline))
            it.rooms.add(savedRoom.id!!)
            usersRepository.save(it)
            return@map savedRoom
        }.orElseThrow { UserNotFoundException() }
    }

    override fun findRoomsByUserId(userId: String): MutableList<Room> {
        val user = usersRepository.findById(userId)
        return user.map {
            return@map roomsRepository.findAllById(it.rooms).toMutableList()
        }.orElseThrow { UserNotFoundException() }
    }
}