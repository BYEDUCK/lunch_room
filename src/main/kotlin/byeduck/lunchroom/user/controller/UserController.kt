package byeduck.lunchroom.user.controller

import byeduck.lunchroom.NICK_COOKIE_NAME
import byeduck.lunchroom.TOKEN_COOKIE_NAME
import byeduck.lunchroom.USER_ID_COOKIE_NAME
import byeduck.lunchroom.token.AuthorizationToken
import byeduck.lunchroom.user.oauth.OAuthService
import byeduck.lunchroom.user.service.UserAuthenticationService
import byeduck.lunchroom.user.service.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid
import javax.validation.constraints.NotBlank

@RestController
@RequestMapping(value = ["users"])
class UserController(
        @Autowired
        private val userAuthenticationService: UserAuthenticationService,
        @Autowired
        private val userService: UserService,
        @Autowired
        private val googleOAuthService: OAuthService,
        @Value("\${front.domain}")
        private val frontDomain: String
) {

    private val logger: Logger = LoggerFactory.getLogger(UserController::class.java)

    @PostMapping(value = ["signUp"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    fun signUp(@Valid @RequestBody signUpRequest: SignRequest) {
        logger.info("Sign up by user \"{}\"", signUpRequest.nick)
        userAuthenticationService.signUp(signUpRequest.nick, signUpRequest.password)
    }

    @PostMapping(value = ["signIn"], consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun signIn(
            @Valid @RequestBody signRequest: SignRequest,
            response: HttpServletResponse
    ): ResponseEntity<Any> {
        logger.info("Sign in by user \"{}\"", signRequest.nick)
        val confirmation = userAuthenticationService.signIn(signRequest.nick, signRequest.password)
        setAuthorizationCookies(response, confirmation.userNick, confirmation.userId, confirmation.token)
        return ResponseEntity(HttpStatus.OK)
    }

    @PostMapping(value = ["signIn/oauth/google"])
    fun signInGoogleOAuth(
            @NotBlank @RequestParam("code") authorizationCode: String,
            response: HttpServletResponse
    ): ResponseEntity<Any> {
        logger.info("Authorizing user with google oauth")
        val confirmation = googleOAuthService.sign(authorizationCode)
        setAuthorizationCookies(response, confirmation.userNick, confirmation.userId, confirmation.token)
        return ResponseEntity(HttpStatus.OK)
    }

    @GetMapping(value = ["checkNick"])
    fun isNickAvailable(@RequestParam("nick") nick: String): ResponseEntity<Any> {
        return if (userService.isNickAvailable(nick))
            ResponseEntity.status(HttpStatus.OK).build()
        else
            ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build()
    }

    private fun setAuthorizationCookies(
            response: HttpServletResponse, userNick: String, userId: String, token: AuthorizationToken
    ) {
        val nickCookie = Cookie(NICK_COOKIE_NAME, userNick)
        val tokenCookie = Cookie(TOKEN_COOKIE_NAME, token.data)
        val userIdCookie = Cookie(USER_ID_COOKIE_NAME, userId)
        nickCookie.path = "/"
        tokenCookie.path = "/"
        userIdCookie.path = "/"
        nickCookie.domain = frontDomain
        tokenCookie.domain = frontDomain
        userIdCookie.domain = frontDomain
        tokenCookie.maxAge = ((token.expiresOn - System.currentTimeMillis()) / 1000.0).toInt()
        response.addCookie(nickCookie)
        response.addCookie(tokenCookie)
        response.addCookie(userIdCookie)
    }

}