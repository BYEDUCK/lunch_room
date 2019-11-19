package byeduck.lunchroom.user.controller

data class SignedInUser(
        val userId: String?,
        val token: String
)