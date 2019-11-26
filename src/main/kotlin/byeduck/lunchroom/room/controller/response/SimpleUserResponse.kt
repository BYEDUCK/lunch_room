package byeduck.lunchroom.room.controller.response

import byeduck.lunchroom.domain.User

data class SimpleUserResponse(
        var nick: String,
        var id: String
) {
    companion object {
        fun fromUser(user: User): SimpleUserResponse {
            return SimpleUserResponse(user.nick, user.id!!)
        }
    }
}