package byeduck.lunchroom.user.services

interface TokenService {
    fun generateToken(subject: String): String
    fun validateToken(token: String, nick: String): Boolean
}