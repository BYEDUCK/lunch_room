package byeduck.lunchroom.repositories

import byeduck.lunchroom.domain.LunchProposal
import org.springframework.data.mongodb.repository.MongoRepository

interface LunchRepository : MongoRepository<LunchProposal, String>