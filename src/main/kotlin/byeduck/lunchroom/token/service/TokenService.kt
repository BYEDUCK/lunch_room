package byeduck.lunchroom.token.service

interface TokenService {
    fun generateToken(subject: String): String
    fun validateToken(token: String, nick: String)
    fun getSubject(token: String): String
}