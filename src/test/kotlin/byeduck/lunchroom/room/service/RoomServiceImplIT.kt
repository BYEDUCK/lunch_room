package byeduck.lunchroom.room.service

import byeduck.lunchroom.domain.RoomUser
import byeduck.lunchroom.domain.User
import byeduck.lunchroom.repositories.UsersRepository
import byeduck.lunchroom.room.exceptions.RoomAlreadyExistsException
import byeduck.lunchroom.user.exceptions.UserNotFoundException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext

@SpringBootTest
internal class RoomServiceImplIT {

    private val testUserNick = "testNick"
    private val testRoomName = "testName"
    private val testUser = User(testUserNick, ByteArray(0), ByteArray(0))
    private lateinit var deadlines: Deadlines

    @Autowired
    private lateinit var roomService: RoomService

    @Autowired
    private lateinit var usersRepository: UsersRepository

    @Value("\${user.start.points}")
    private lateinit var userStartingPoints: String

    @BeforeEach
    internal fun setUp() {
        val current = System.currentTimeMillis()
        deadlines = Deadlines(current + 10000, current + 20000, current + 30000)
    }

    @Test
    @DisplayName("When adding new room - owner id should be set and room shall be added to owners rooms list")
    @DirtiesContext
    internal fun testAddNewRoomForTestUserValid() {
        val owner = usersRepository.save(testUser)
        val savedRoom = roomService.addRoom(testRoomName, owner.id!!, deadlines)

        assertNotNull(savedRoom.id)
        assertEquals(savedRoom.owner, owner.id)

        val updatedOwner = usersRepository.findById(owner.id!!)
        assertThat(updatedOwner.get().rooms).containsExactly(savedRoom.id)
        assertThat(savedRoom.users).containsExactly(RoomUser(updatedOwner.get(), userStartingPoints.toInt()))
    }

    @Test
    @DisplayName("When adding room while a room with given name already exists - exception should be thrown")
    @DirtiesContext
    internal fun testAddAlreadyInsertedRoom() {
        val owner = usersRepository.save(testUser)
        roomService.addRoom(testRoomName, owner.id!!, deadlines)

        assertThrows<RoomAlreadyExistsException> {
            roomService.addRoom(testRoomName, owner.id!!, deadlines)
        }
    }

    @Test
    @DisplayName("When adding room by user that does not exist - exception should be thrown")
    internal fun testAddRoomForNotExistingUser() {
        assertThrows<UserNotFoundException> {
            roomService.addRoom(testRoomName, "notSuchUser", deadlines)
        }
    }

    @Test
    @DisplayName("When joining the room - its id should be added to user and the user should be added to room")
    @DirtiesContext
    internal fun testJoinRoomValid() {
        var owner = usersRepository.save(testUser)
        var otherUser = usersRepository.save(User("otherUser", ByteArray(0), ByteArray(0)))
        val addedRoom = roomService.addRoom(testRoomName, owner.id!!, deadlines)
        val room = roomService.joinRoomById(addedRoom.id!!, otherUser.id!!)
        otherUser = usersRepository.findById(otherUser.id!!).get()
        owner = usersRepository.findByNick(testUserNick).get()

        assertThat(room.users).containsExactly(
                RoomUser(owner, userStartingPoints.toInt()), RoomUser(otherUser, userStartingPoints.toInt())
        )
        assertThat(otherUser.rooms).containsExactly(room.id)
        assertThat(owner.rooms).containsExactly(room.id)
    }
}