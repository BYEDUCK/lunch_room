package byeduck.lunchroom.room.service

import byeduck.lunchroom.domain.Room

interface RoomService {
    fun addRoom(name: String, ownerId: String, signDeadline: Long, postDeadline: Long, priorityDeadline: Long): Room
    fun findRoomsByUserId(userId: String): MutableList<Room>
}