package byeduck.lunchroom.user.oauth

import com.fasterxml.jackson.annotation.JsonProperty

data class OAuthAccessTokenResponse(
        @field:JsonProperty("access_token") val accessToken: String,
        @field:JsonProperty("expires_in") val expirationTime: Long,
        @field:JsonProperty("refresh_token") val refreshToken: String
)