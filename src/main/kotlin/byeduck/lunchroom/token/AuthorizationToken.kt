package byeduck.lunchroom.token

data class AuthorizationToken(
        val data: String,
        val expiresOn: Long
)