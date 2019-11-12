package byeduck.lunchroom.user

import byeduck.lunchroom.user.services.UserService
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class UserServiceImplIT {

    @Autowired
    private lateinit var userService: UserService

    @Test
    @DisplayName("After saving user with given password signIn should return saved user with token")
    internal fun basicLoginActionTest() {
        val nick = "testNick"
        val password = "testPassword"
        userService.signUp(nick, password)
        val loggedInUser = userService.signIn(nick, password)
        assertTrue(loggedInUser.user.id?.isNotEmpty()!!)
        assertTrue(loggedInUser.token.isNotEmpty())
    }
}