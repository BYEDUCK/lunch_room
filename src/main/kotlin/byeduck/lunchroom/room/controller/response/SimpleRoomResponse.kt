package byeduck.lunchroom.room.controller.response

import byeduck.lunchroom.domain.Room

data class SimpleRoomResponse(
        val roomId: String,
        val roomName: String,
        val signDeadline: Long,
        val postDeadline: Long,
        val voteDeadline: Long
) {
    companion object {
        fun fromRoom(room: Room): SimpleRoomResponse {
            return SimpleRoomResponse(room.id!!, room.name, room.signDeadline, room.postDeadline, room.voteDeadline)
        }
    }
}