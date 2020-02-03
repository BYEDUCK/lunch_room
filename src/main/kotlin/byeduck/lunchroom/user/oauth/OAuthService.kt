package byeduck.lunchroom.user.oauth

import byeduck.lunchroom.user.controller.AuthenticationConfirm

interface OAuthService {
    fun sign(authorizationCode: String): AuthenticationConfirm
}