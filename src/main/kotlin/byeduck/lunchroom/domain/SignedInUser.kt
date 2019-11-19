package byeduck.lunchroom.domain

data class SignedInUser(
        val userId: String?,
        val token: String
)