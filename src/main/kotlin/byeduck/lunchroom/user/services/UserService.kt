package byeduck.lunchroom.user.services

import byeduck.lunchroom.domain.SignedInUser

interface UserService {
    fun signIn(nick: String, password: String): SignedInUser
    fun signUp(nick: String, password: String)
}