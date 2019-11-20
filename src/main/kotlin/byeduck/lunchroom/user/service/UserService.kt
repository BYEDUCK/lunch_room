package byeduck.lunchroom.user.service

interface UserService {
    fun isNickAvailable(nick: String): Boolean
}