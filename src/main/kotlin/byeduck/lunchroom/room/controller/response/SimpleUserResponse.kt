package byeduck.lunchroom.room.controller.response

import byeduck.lunchroom.domain.RoomUser

data class SimpleUserResponse(
        var nick: String,
        var id: String,
        var points: Int
) {
    companion object {
        fun fromUser(roomUser: RoomUser): SimpleUserResponse {
            return SimpleUserResponse(roomUser.user.nick, roomUser.user.id!!, roomUser.points)
        }
    }
}