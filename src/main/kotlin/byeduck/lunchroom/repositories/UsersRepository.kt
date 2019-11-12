package byeduck.lunchroom.repositories

import byeduck.lunchroom.domain.User
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface UsersRepository : MongoRepository<User, String> {
    fun findByNick(nick: String): Optional<User>
}