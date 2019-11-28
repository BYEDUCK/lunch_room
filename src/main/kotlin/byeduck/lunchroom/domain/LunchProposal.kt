package byeduck.lunchroom.domain

import org.springframework.data.annotation.Id

data class LunchProposal(
        @Id
        var id: String?,
        var roomId: String,
        var menuItems: MutableList<MenuItem>,
        var ratingSum: Int = 0,
        var votesCount: Int = 0
) {
    constructor(roomId: String, menuItems: MutableList<MenuItem>) : this(null, roomId, menuItems)

    fun voteFor(rating: Int): LunchProposal {
        val proposal = LunchProposal(this.id, this.roomId, this.menuItems, this.ratingSum, this.votesCount)
        proposal.votesCount++
        proposal.ratingSum += rating
        return proposal
    }
}