package byeduck.lunchroom.user.controller

import byeduck.lunchroom.domain.SignedInUser
import byeduck.lunchroom.user.service.UserAuthenticationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/users"])
class UserController(
        @Autowired
        private val userService: UserAuthenticationService
) {

    @PostMapping(value = ["/signUp"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    fun signUp(@RequestBody signUpRequest: SignRequest) {
        userService.signUp(signUpRequest.nick, signUpRequest.password)
    }

    @PostMapping(value = ["signIn"], consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun signIn(@RequestBody signRequest: SignRequest): SignedInUser {
        return userService.signIn(signRequest.nick, signRequest.password)
    }

}