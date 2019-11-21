package byeduck.lunchroom.user.controller

import byeduck.lunchroom.user.service.UserAuthenticationService
import byeduck.lunchroom.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping(value = ["/users"])
class UserController(
        @Autowired
        private val userAuthenticationService: UserAuthenticationService,
        @Autowired
        private val userService: UserService
) {

    @PostMapping(value = ["/signUp"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    fun signUp(@Valid @RequestBody signUpRequest: SignRequest) {
        userAuthenticationService.signUp(signUpRequest.nick, signUpRequest.password)
    }

    @PostMapping(value = ["signIn"], consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun signIn(@RequestBody signRequest: SignRequest): SignedInUser {
        return userAuthenticationService.signIn(signRequest.nick, signRequest.password)
    }

    @GetMapping(value = ["checkNick"])
    fun isNickAvailable(@RequestParam("nick") nick: String): ResponseEntity<Any> {
        return if (userService.isNickAvailable(nick))
            ResponseEntity.status(HttpStatus.OK).build()
        else
            ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build()
    }

}