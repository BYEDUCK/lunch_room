package byeduck.lunchroom.repositories

import byeduck.lunchroom.domain.Lottery
import org.springframework.data.mongodb.repository.MongoRepository

interface LotteryRepository : MongoRepository<Lottery, String> {
    fun countByUserId(userId: String): Int
}