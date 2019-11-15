package byeduck.lunchroom.token

import byeduck.lunchroom.NICK_HEADER_NAME
import byeduck.lunchroom.TOKEN_HEADER_NAME
import byeduck.lunchroom.error.exceptions.InvalidTokenException
import byeduck.lunchroom.token.service.TokenService
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component

@Aspect
@Component
class TokenValidator(
        @Autowired
        private val tokenService: TokenService
) {

    @Before("@annotation(byeduck.lunchroom.token.ValidateToken)")
    fun validateToken(joinPoint: JoinPoint) {
        var checked = false
        for (arg in joinPoint.args) {
            if (arg is HttpHeaders) {
                validateAuthorizationHeaders(arg)
                checked = true
                break
            }
        }
        if (!checked) {
            throw InvalidTokenException()
        }
    }

    private fun validateAuthorizationHeaders(headers: HttpHeaders) {
        val userNick = headers.getFirst(NICK_HEADER_NAME)
        val token = headers.getFirst(TOKEN_HEADER_NAME)
        if (userNick == null || token == null || !tokenService.validateToken(token, userNick)) {
            throw InvalidTokenException()
        }
    }

}