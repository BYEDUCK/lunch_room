package byeduck.lunchroom.room.service

data class Summary(
        val timestamp: Long,
        val winnerNick: String,
        val winnerProposalTitle: String
)