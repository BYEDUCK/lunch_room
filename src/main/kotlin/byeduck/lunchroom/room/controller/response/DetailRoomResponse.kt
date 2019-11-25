package byeduck.lunchroom.room.controller.response

import byeduck.lunchroom.domain.Room

class DetailRoomResponse(
        val id: String,
        val name: String,
        val owner: String,
        val signDeadline: Long,
        val postDeadline: Long,
        val priorityDeadline: Long,
        val users: MutableList<String>
) {
    companion object {
        fun fromRoom(room: Room): DetailRoomResponse {
            return DetailRoomResponse(room.id!!, room.name, room.owner, room.signDeadline, room.postDeadline, room.priorityDeadline, room.users)
        }
    }
}