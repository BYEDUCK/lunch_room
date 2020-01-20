package byeduck.lunchroom.user.oauth.google

import com.fasterxml.jackson.annotation.JsonProperty

data class GoogleUserInfoResponse(
        @field:JsonProperty("id") val id: String,
        @field:JsonProperty("email") val email: String,
        @field:JsonProperty("verified_email") val isEmailVerified: Boolean,
        @field:JsonProperty("picture") val pictureUrl: String
)