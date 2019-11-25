package byeduck.lunchroom.room.controller.response

import byeduck.lunchroom.domain.User

data class SimpleUser(
        var nick: String,
        var id: String
) {
    companion object {
        fun fromUser(user: User): SimpleUser {
            return SimpleUser(user.nick, user.id!!)
        }
    }
}