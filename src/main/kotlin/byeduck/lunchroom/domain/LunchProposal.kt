package byeduck.lunchroom.domain

import org.springframework.data.annotation.Id

data class LunchProposal(
        @Id
        var id: String?,
        var roomId: String,
        var title: String,
        var menuUrl: String,
        var menuItems: List<MenuItem>,
        var proposalOwnerId: String,
        var ratingSum: Int = 0,
        var votesCount: Int = 0
) {
    constructor(roomId: String, title: String, menuUrl: String, menuItems: List<MenuItem>, proposalOwnerId: String)
            : this(null, roomId, title, menuUrl, menuItems, proposalOwnerId)
}