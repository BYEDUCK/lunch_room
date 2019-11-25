package byeduck.lunchroom.room.controller.response

import byeduck.lunchroom.domain.Room

class DetailRoomResponse(
        val id: String,
        val name: String,
        val owner: String,
        val signDeadline: Long,
        val postDeadline: Long,
        val voteDeadline: Long,
        val users: List<SimpleUser>
) {
    companion object {
        fun fromRoom(room: Room): DetailRoomResponse {
            return DetailRoomResponse(
                    room.id!!, room.name, room.owner, room.signDeadline, room.postDeadline,
                    room.voteDeadline, room.users.map { SimpleUser.fromUser(it) }
            )
        }
    }
}