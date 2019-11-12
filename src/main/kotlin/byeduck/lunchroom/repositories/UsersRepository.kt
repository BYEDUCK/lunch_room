package byeduck.lunchroom.repositories

import byeduck.lunchroom.domain.User
import org.springframework.data.mongodb.repository.MongoRepository

interface UsersRepository : MongoRepository<User, String> {
}