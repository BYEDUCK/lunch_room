package byeduck.lunchroom.user

import byeduck.lunchroom.domain.User

interface UserService {
    fun checkPassword(nick: String, password: String): Boolean
    fun saveUser(nick: String, password: String): User
}