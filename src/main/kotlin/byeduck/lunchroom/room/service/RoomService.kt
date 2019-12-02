package byeduck.lunchroom.room.service

import byeduck.lunchroom.domain.Room

interface RoomService {
    fun addRoom(name: String, ownerId: String, deadlines: Deadlines): Room
    fun findRoomsByUserId(userId: String): MutableList<Room>
    fun joinRoomById(roomId: String, userId: String): Room
    fun joinRoomByName(roomName: String, userId: String): Room
    fun deleteRoom(id: String, token: String)
    fun updateRoom(roomId: String, token: String, newDeadlines: Deadlines): Room
}