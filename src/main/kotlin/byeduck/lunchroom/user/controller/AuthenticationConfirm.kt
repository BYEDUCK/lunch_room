package byeduck.lunchroom.user.controller

data class AuthenticationConfirm(
        val userId: String,
        val userNick: String,
        val token: String
)