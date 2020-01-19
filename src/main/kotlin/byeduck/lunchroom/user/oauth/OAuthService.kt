package byeduck.lunchroom.user.oauth

import byeduck.lunchroom.user.controller.SignResponse

interface OAuthService {
    fun sign(authorizationCode: String): SignResponse
}