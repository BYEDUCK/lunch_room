package byeduck.lunchroom.repositories

import byeduck.lunchroom.domain.Room
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest

@DataMongoTest
class RoomRepositoryIT {

    private val testName: String = "test"

    @Autowired
    private lateinit var roomsRepository: RoomsRepository;

    @Test
    @DisplayName("Without any operation db should be empty")
    internal fun testFindAllWithEmptyDatabase() {
        val rooms = roomsRepository.findAll()
        assertTrue(rooms.isEmpty())
    }

    @Test
    @DisplayName("Simple scenario: insert (check if id exists) -> remove (check if db is empty)")
    internal fun testInsertFindRemoveScenario() {
        val room = Room(null, testName, testName, 1L, 1L, 1L)
        val savedRoom = roomsRepository.save(room)
        val id = savedRoom.id ?: ""
        assertTrue(id.isNotEmpty())
        val foundRoom = roomsRepository.findById(id)
        assertEquals(savedRoom, foundRoom.get())
        roomsRepository.delete(foundRoom.get())
        assertEquals(0L, roomsRepository.count())
    }
}