package byeduck.lunchroom.room.service

import byeduck.lunchroom.domain.Lottery
import byeduck.lunchroom.domain.Room

interface RoomService {
    fun addRoom(name: String, ownerId: String, deadlines: Deadlines): Room
    fun findRoomsByUserId(userId: String): MutableList<Room>
    fun findRoomByName(name: String): Room
    fun joinRoomById(roomId: String, userId: String): Room
    fun deleteRoom(id: String, token: String)
    fun updateRoom(roomId: String, token: String, newDeadlines: Deadlines): Room
    fun doTheLottery(userId: String, roomId: String, token: String): Lottery
}