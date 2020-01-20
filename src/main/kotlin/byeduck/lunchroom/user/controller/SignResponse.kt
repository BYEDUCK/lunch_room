package byeduck.lunchroom.user.controller

data class SignResponse(
        val userId: String?,
        val userNick: String,
        val token: String
)