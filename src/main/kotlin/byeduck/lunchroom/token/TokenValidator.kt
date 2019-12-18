package byeduck.lunchroom.token

import byeduck.lunchroom.NICK_HEADER_NAME
import byeduck.lunchroom.TOKEN_HEADER_NAME
import byeduck.lunchroom.error.exceptions.InvalidTokenException
import byeduck.lunchroom.token.service.TokenService
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.util.MultiValueMap

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
            if (arg is MultiValueMap<*, *>) {
                validateAuthorizationHeaders(arg)
                checked = true
                break
            }
        }
        if (!checked) {
            throw InvalidTokenException()
        }
    }

    private fun validateAuthorizationHeaders(headers: MultiValueMap<*, *>) {
        val userNick = headers[NICK_HEADER_NAME] ?: throw InvalidTokenException()
        val token = headers[TOKEN_HEADER_NAME] ?: throw InvalidTokenException()
        tokenService.validateToken(token.first() as String, userNick.first() as String)
    }

}