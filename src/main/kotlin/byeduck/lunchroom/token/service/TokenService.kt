package byeduck.lunchroom.token.service

import byeduck.lunchroom.token.AuthorizationToken

interface TokenService {
    fun generateToken(subject: String): AuthorizationToken
    fun validateToken(token: String, nick: String)
    fun getSubject(token: String): String
}