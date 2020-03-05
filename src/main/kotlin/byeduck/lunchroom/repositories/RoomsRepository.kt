package byeduck.lunchroom.repositories

import byeduck.lunchroom.domain.Room
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface RoomsRepository : MongoRepository<Room, String> {
    fun findByName(name: String): Optional<Room>
    fun findByNameLikeIgnoreCase(name: String): List<Room>
}