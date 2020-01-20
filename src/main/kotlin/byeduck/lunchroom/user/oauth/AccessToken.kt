package byeduck.lunchroom.user.oauth

data class AccessToken(
        val accessToken: String,
        val expirationTime: Long,
        val refreshToken: String?
) {
    companion object {
        fun fromOAuthAccessTokenResponse(response: OAuthAccessTokenResponse): AccessToken =
                AccessToken(response.accessToken, response.expirationTime, response.refreshToken)
    }
}