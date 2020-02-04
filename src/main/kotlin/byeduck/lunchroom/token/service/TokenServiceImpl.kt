package byeduck.lunchroom.token.service

import byeduck.lunchroom.error.exceptions.InvalidTokenException
import byeduck.lunchroom.token.AuthorizationToken
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

    override fun generateToken(subject: String): AuthorizationToken {
        val now = System.currentTimeMillis()
        val expiresOn = now + tokenValidity
        val token = Jwts.builder()
                .setClaims(HashMap<String, Any>() as Map<String, Any>?)
                .setSubject(subject)
                .setIssuedAt(Date(now))
                .setExpiration(Date(expiresOn))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact()
        return AuthorizationToken(token, expiresOn)
    }

    override fun validateToken(token: String, nick: String) {
        val claims = getAllClaimsForToken(token)
        val subject = claims.subject
        if (nick != subject || isTokenExpired(token)) {
            throw InvalidTokenException()
        }
    }

    override fun getSubject(token: String): String {
        return getAllClaimsForToken(token).subject
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