package byeduck.lunchroom.user

import org.junit.jupiter.api.Assertions.assertNotEquals
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
    @DisplayName("After saving user with given support it should return true for check")
    internal fun basicLoginActionTest() {
        val nick = "testNick"
        val password = "testPassword"
        val loggedInUser = userService.saveUser(nick, password)
        assertNotEquals(password, loggedInUser.password)
        assertTrue(userService.checkPassword(nick, password))
    }
}