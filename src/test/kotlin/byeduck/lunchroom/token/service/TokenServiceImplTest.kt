package byeduck.lunchroom.token.service

import byeduck.lunchroom.error.exceptions.InvalidTokenException
import io.jsonwebtoken.JwtException
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class TokenServiceImplTest {

    @Autowired
    private lateinit var tokenService: TokenService

    @Value("\${jwt.token.validity}")
    private lateinit var tokenValidity: String

    private val userNick = "testNick"

    @Test
    @DisplayName("Generated token should be valid right away")
    internal fun testValidateCorrectToken() {
        val token = tokenService.generateToken(userNick)
        assertDoesNotThrow { tokenService.validateToken(token, userNick) }
    }

    @Test
    @DisplayName("Generated token after (validity + 1)ms should not be valid")
    internal fun testValidateExpiredToken() {
        val validity = tokenValidity.toLong()
        val token = tokenService.generateToken(userNick)
        Thread.sleep(validity + 1)
        assertThrows<JwtException> { tokenService.validateToken(token, userNick) }
    }

    @Test
    @DisplayName("Token generated for different user should not be valid")
    internal fun testValidateWrongSubjectToken() {
        val token = tokenService.generateToken(userNick)
        assertThrows<InvalidTokenException> { tokenService.validateToken(token, "differentNick") }
    }
}