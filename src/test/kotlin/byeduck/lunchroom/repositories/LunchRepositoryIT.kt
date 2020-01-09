package byeduck.lunchroom.repositories

import byeduck.lunchroom.domain.LunchProposal
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest

@DataMongoTest
internal class LunchRepositoryIT {

    @Autowired
    private lateinit var lunchRepository: LunchRepository

    @Test
    internal fun testFindAllByRoomId() {
        val roomId = "123"
        val title = "title"
        val url = "url"
        val ownerId = "123"
        val proposal1 = LunchProposal(roomId, title, url, ArrayList(), ownerId)
        val proposal2 = LunchProposal(roomId, title, url, ArrayList(), ownerId)
        val proposal3 = LunchProposal(roomId, title, url, ArrayList(), ownerId)
        val saved1 = lunchRepository.insert(proposal1)
        val saved2 = lunchRepository.insert(proposal2)
        val saved3 = lunchRepository.insert(proposal3)

        val lunchProposals = lunchRepository.findAllByRoomId(roomId)
        assertThat(lunchProposals).hasSize(3).containsExactly(saved1, saved2, saved3)
    }
}