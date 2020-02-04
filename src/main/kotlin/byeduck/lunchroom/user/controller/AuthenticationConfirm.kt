package byeduck.lunchroom.user.controller

import byeduck.lunchroom.token.AuthorizationToken

data class AuthenticationConfirm(
        val userId: String,
        val userNick: String,
        val token: AuthorizationToken
)