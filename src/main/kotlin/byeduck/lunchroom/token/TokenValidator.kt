package byeduck.lunchroom.token

import byeduck.lunchroom.error.exceptions.InvalidTokenException
import byeduck.lunchroom.token.service.TokenService
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Aspect
@Component
class TokenValidator(
        @Autowired
        private val tokenService: TokenService
) {

    @Before("@annotation(byeduck.lunchroom.token.ValidateToken)")
    fun validateToken(joinPoint: JoinPoint) {
        for (arg in joinPoint.args) {
            if (arg is TokenRequest && !tokenService.validateToken(arg.userToken, arg.userName)) {
                throw InvalidTokenException()
            }
        }
    }

}