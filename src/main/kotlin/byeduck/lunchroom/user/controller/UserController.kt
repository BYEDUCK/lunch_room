package byeduck.lunchroom.user.controller

import byeduck.lunchroom.domain.SignedInUser
import byeduck.lunchroom.user.service.UserAuthenticationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
        @Autowired
        private val userService: UserAuthenticationService
) {

    @PostMapping("/user/signUp")
    @ResponseStatus(HttpStatus.CREATED)
    fun signUp(@RequestBody signUpRequest: SignRequest) {
        userService.signUp(signUpRequest.nick, signUpRequest.password)
    }

    @PostMapping("/user/signIn")
    fun signIn(@RequestBody signRequest: SignRequest): SignedInUser {
        return userService.signIn(signRequest.nick, signRequest.password)
    }

}