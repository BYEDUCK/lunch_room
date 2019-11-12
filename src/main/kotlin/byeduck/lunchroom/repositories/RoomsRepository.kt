package byeduck.lunchroom.repositories

import byeduck.lunchroom.domain.Room
import org.springframework.data.mongodb.repository.MongoRepository

interface RoomsRepository : MongoRepository<Room, String> {
}