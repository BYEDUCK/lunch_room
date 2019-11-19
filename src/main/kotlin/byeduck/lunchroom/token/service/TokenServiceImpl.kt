package byeduck.lunchroom.token.service

import byeduck.lunchroom.error.exceptions.InvalidTokenException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*
import kotlin.collections.HashMap

@Service
class TokenServiceImpl(
        @Value("\${jwt.secret}")
        private val secret: String,
        @Value("\${jwt.token.validity}")
        private val tokenValidity: Long
) : TokenService {

    override fun generateToken(subject: String): String {
        val currentMillis = System.currentTimeMillis()
        return Jwts.builder()
                .setClaims(HashMap<String, Any>() as Map<String, Any>?)
                .setSubject(subject)
                .setIssuedAt(Date(currentMillis))
                .setExpiration(Date(currentMillis + tokenValidity))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact()
    }

    override fun validateToken(token: String, nick: String) {
        val claims = getAllClaimsForToken(token)
        val subject = claims.subject
        if (nick != subject || isTokenExpired(token)) {
            throw InvalidTokenException()
        }
    }

    private fun isTokenExpired(token: String): Boolean {
        val claims = getAllClaimsForToken(token)
        return claims.expiration.before(Date())
    }

    private fun getAllClaimsForToken(token: String): Claims {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .body
    }
}