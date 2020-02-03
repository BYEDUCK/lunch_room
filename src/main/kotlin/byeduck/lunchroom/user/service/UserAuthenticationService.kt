package byeduck.lunchroom.user.service

import byeduck.lunchroom.user.controller.AuthenticationConfirm

interface UserAuthenticationService {
    fun signIn(nick: String, password: String): AuthenticationConfirm
    fun signUp(nick: String, password: String)
}