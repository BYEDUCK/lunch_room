package byeduck.lunchroom.room.service

import byeduck.lunchroom.domain.Room

interface RoomService {
    fun addRoom(name: String, ownerId: String, deadlines: Deadlines): Room
    fun findRoomsByUserId(userId: String): MutableList<Room>
    fun joinRoom(name: String, userId: String): Room
}