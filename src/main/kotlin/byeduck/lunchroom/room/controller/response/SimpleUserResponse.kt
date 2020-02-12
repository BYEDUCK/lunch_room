package byeduck.lunchroom.room.controller.response

import byeduck.lunchroom.domain.RoomUser

data class SimpleUserResponse(
        var id: String,
        var nick: String,
        var points: Int
) {
    companion object {
        fun fromUser(roomUser: RoomUser): SimpleUserResponse {
            return SimpleUserResponse(roomUser.user.id!!, roomUser.user.nick, roomUser.points)
        }
    }
}