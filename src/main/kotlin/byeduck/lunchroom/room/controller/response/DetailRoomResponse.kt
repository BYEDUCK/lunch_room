package byeduck.lunchroom.room.controller.response

import byeduck.lunchroom.domain.Room

class DetailRoomResponse(
        val roomId: String,
        val roomName: String,
        val ownerId: String,
        val signDeadline: Long,
        val postDeadline: Long,
        val voteDeadline: Long,
        val users: List<SimpleUserResponse>
) {
    companion object {
        fun fromRoom(room: Room): DetailRoomResponse {
            return DetailRoomResponse(
                    room.id!!, room.name, room.owner, room.signDeadline, room.postDeadline,
                    room.voteDeadline, room.users.map { SimpleUserResponse.fromUser(it) }
            )
        }
    }
}