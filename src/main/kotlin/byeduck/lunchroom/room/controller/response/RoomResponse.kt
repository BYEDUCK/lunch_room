package byeduck.lunchroom.room.controller.response

import byeduck.lunchroom.domain.Room

data class RoomResponse(
        val roomId: String,
        val roomName: String,
        val ownerId: String,
        val initialDeadline: Long,
        val voteDeadline: Long,
        val open: Boolean
) {
    companion object {
        fun fromRoom(room: Room): RoomResponse {
            return RoomResponse(
                    room.id!!, room.name, room.owner, room.initialDeadline, room.voteDeadline, room.open
            )
        }
    }
}