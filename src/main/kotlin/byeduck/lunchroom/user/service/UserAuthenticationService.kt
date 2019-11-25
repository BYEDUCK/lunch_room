package byeduck.lunchroom.user.service

import byeduck.lunchroom.user.controller.SignResponse

interface UserAuthenticationService {
    fun signIn(nick: String, password: String): SignResponse
    fun signUp(nick: String, password: String)
}