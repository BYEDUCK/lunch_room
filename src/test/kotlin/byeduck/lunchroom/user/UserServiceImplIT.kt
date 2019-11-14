package byeduck.lunchroom.user

import byeduck.lunchroom.user.exceptions.InvalidCredentialsException
import byeduck.lunchroom.user.exceptions.UserAlreadyExistsException
import byeduck.lunchroom.user.service.UserAuthenticationService
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext

@SpringBootTest
internal class UserServiceImplIT {

    @Autowired
    private lateinit var userService: UserAuthenticationService

    @Test
    @DisplayName("After saving user with given password signIn should return saved user with token")
    @DirtiesContext
    internal fun basicLoginActionTest() {
        val nick = "testNick"
        val password = "testPassword"
        userService.signUp(nick, password)
        val loggedInUser = userService.signIn(nick, password)
        assertTrue(loggedInUser.user.id?.isNotEmpty()!!)
        assertTrue(loggedInUser.token.isNotEmpty())
    }

    @Test
    @DisplayName("Signing up with user that has been already signed up should result in exception")
    @DirtiesContext
    internal fun testSignUpWithAlreadySavedNick() {
        val nick = "testNick"
        val password = "testPassword"
        userService.signUp(nick, password)
        assertThrows<UserAlreadyExistsException> { userService.signUp(nick, password) }
    }

    @Test
    @DisplayName("Signing in with wrong password should result in exception")
    @DirtiesContext
    internal fun testSignInWIthWrongPassword() {
        val nick = "testNick"
        val password = "testPassword"
        userService.signUp(nick, password)
        assertThrows<InvalidCredentialsException> { userService.signIn(nick, "wrongPassword") }
        assertDoesNotThrow { userService.signIn(nick, password) }
    }
}