package byeduck.lunchroom.token.service

interface TokenService {
    fun generateToken(subject: String): String
    fun validateToken(token: String, nick: String): Boolean
}