package byeduck.lunchroom.room.service

import byeduck.lunchroom.domain.User
import byeduck.lunchroom.repositories.UsersRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class RoomServiceImplIT {

    private val testUserNick = "testNick"
    private val testRoomName = "testName"
    private val testUser = User(testUserNick, ByteArray(0), ByteArray(0), ArrayList())

    @Autowired
    private lateinit var roomService: RoomService

    @Autowired
    private lateinit var usersRepository: UsersRepository

    @Test
    @DisplayName("When adding new room - owner id should be set and room shall be added to owners rooms list")
    internal fun testAddNewRoomForTestUserValid() {
        usersRepository.save(testUser)
        val savedRoom = roomService.addRoom(testRoomName, testUserNick, 1L, 1L, 1L)
        val roomOwner = usersRepository.findByNick(testUserNick).get()

        assertNotNull(savedRoom.id)
        assertNotNull(roomOwner.id)
        assertEquals(savedRoom.owner, roomOwner.id)
        assertThat(roomOwner.rooms).containsExactly(savedRoom.id)
    }
}