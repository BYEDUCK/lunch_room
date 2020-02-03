package byeduck.lunchroom.room.controller

import byeduck.lunchroom.error.exceptions.InvalidTokenException
import byeduck.lunchroom.room.controller.request.CreateRoomRequest
import byeduck.lunchroom.room.service.Deadlines
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType

@SpringBootTest
internal class RoomControllerIT {

    @Autowired
    private lateinit var roomController: RoomController

    @Test
    @DisplayName("Add room without nick and token headers should result in exception")
    internal fun testAddRomWithoutRequiredHeaders() {
        assertThrows<InvalidTokenException> {
            val httpHeaders = HttpHeaders()
            httpHeaders.contentType = MediaType.APPLICATION_JSON
            roomController.addRoom(
                    CreateRoomRequest("test", "test", Deadlines(1L, 2L)),
                    httpHeaders
            )
        }
    }
}