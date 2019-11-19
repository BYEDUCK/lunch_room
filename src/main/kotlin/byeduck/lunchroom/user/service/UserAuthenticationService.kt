package byeduck.lunchroom.user.service

import byeduck.lunchroom.user.controller.SignedInUser

interface UserAuthenticationService {
    fun signIn(nick: String, password: String): SignedInUser
    fun signUp(nick: String, password: String)
}