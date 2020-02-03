package byeduck.lunchroom.token

import byeduck.lunchroom.NICK_COOKIE_NAME
import byeduck.lunchroom.TOKEN_COOKIE_NAME
import byeduck.lunchroom.error.exceptions.InvalidTokenException
import byeduck.lunchroom.error.exceptions.UnauthorizedException
import byeduck.lunchroom.token.service.TokenService
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest

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
            if (arg is HttpServletRequest) {
                validateAuthorizationHeaders(arg.cookies)
                checked = true
                break
            }
        }
        if (!checked) {
            throw InvalidTokenException()
        }
    }

    private fun validateAuthorizationHeaders(cookies: Array<Cookie>) {
        var userNick: String? = null
        var token: String? = null
        for (cookie in cookies) {
            when (cookie.name) {
                NICK_COOKIE_NAME -> userNick = cookie.value
                TOKEN_COOKIE_NAME -> token = cookie.value
            }
        }
        tokenService.validateToken(
                token ?: throw UnauthorizedException(),
                userNick ?: throw UnauthorizedException()
        )
    }

}