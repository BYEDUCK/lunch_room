package byeduck.lunchroom.repositories

import byeduck.lunchroom.domain.Room
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.test.annotation.DirtiesContext

@DataMongoTest
internal class RoomRepositoryIT {

    private val testName: String = "test"

    @Autowired
    private lateinit var roomsRepository: RoomsRepository

    @Test
    @DisplayName("Without any operation db should be empty")
    internal fun testFindAllWithEmptyDatabase() {
        val rooms = roomsRepository.findAll()
        assertTrue(rooms.isEmpty())
    }

    @Test
    @DisplayName("Simple scenario: insert (check if id exists) -> remove (check if db is empty)")
    internal fun testInsertFindRemoveScenario() {
        val room = Room(testName, testName, 1L, 1L)
        val savedRoom = roomsRepository.save(room)
        val id = savedRoom.id ?: ""
        assertTrue(id.isNotEmpty())
        val foundRoom = roomsRepository.findById(id)
        assertEquals(savedRoom, foundRoom.get())
        roomsRepository.delete(foundRoom.get())
        assertEquals(0L, roomsRepository.count())
    }

    @Test
    @DirtiesContext
    @DisplayName("Test finding by name like (with case ignoring)")
    internal fun testFindByNameLike() {
        val room1 = Room("testroomName1cake", testName, 1L, 1L)
        val room2 = Room("testroomName2cake", testName, 1L, 1L)
        val room3 = Room("cakeRoomName3test", testName, 1L, 1L)
        val room4 = Room("testRoom", testName, 1L, 1L)

        val saved1 = roomsRepository.save(room1)
        val saved2 = roomsRepository.save(room2)
        val saved3 = roomsRepository.save(room3)
        val saved4 = roomsRepository.save(room4)

        val find1 = roomsRepository.findByNameLikeIgnoreCase("roomName")
        assertThat(find1).hasSize(3).containsExactly(saved1, saved2, saved3)

        val find2 = roomsRepository.findByNameLikeIgnoreCase("testroom")
        assertThat(find2).hasSize(3).containsExactly(saved1, saved2, saved4)

        val find3 = roomsRepository.findByNameLikeIgnoreCase("cake")
        assertThat(find3).hasSize(3).containsExactly(saved1, saved2, saved3)

        val find4 = roomsRepository.findByNameLikeIgnoreCase("test")
        assertThat(find4).hasSize(4).containsExactly(saved1, saved2, saved3, saved4)
    }
}