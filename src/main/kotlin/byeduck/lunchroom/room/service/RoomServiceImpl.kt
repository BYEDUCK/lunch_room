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
    override fun addRoom(
            name: String, ownerNick: String, signDeadline: Long, postDeadline: Long, priorityDeadline: Long
    ): Room {
        val found = roomsRepository.findByName(name)
        if (found.isPresent) {
            throw RoomAlreadyExistsException()
        }
        val owner = usersRepository.findByNick(ownerNick)
        return owner.map {
            val savedRoom = roomsRepository.insert(Room(name, owner.get().id!!, signDeadline, postDeadline, priorityDeadline))
            it.rooms.add(savedRoom.id!!)
            usersRepository.save(it)
            return@map savedRoom
        }.orElseThrow { UserNotFoundException() }
    }
}