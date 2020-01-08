package byeduck.lunchroom.domain

import org.springframework.data.annotation.Id

data class Lottery(
        @Id
        var id: String?,
        var userId: String,
        var userNick: String,
        var roomId: String,
        var proposalId: String,
        var timestamp: Long
) {
    constructor(userId: String, userNick: String, roomId: String, proposalId: String) :
            this(null, userId, userNick, roomId, proposalId, System.currentTimeMillis())
}