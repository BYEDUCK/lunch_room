package byeduck.lunchroom.user.service

import byeduck.lunchroom.domain.SignedInUser

interface UserAuthenticationService {
    fun signIn(nick: String, password: String): SignedInUser
    fun signUp(nick: String, password: String)
}