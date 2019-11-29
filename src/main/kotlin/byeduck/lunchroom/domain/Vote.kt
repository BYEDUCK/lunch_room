package byeduck.lunchroom.domain

data class Vote(
        var proposalId: String,
        var rating: Int
) {

    constructor(proposalId: String) : this(proposalId, -1)

    // ignore rating for equality
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Vote) return false

        if (proposalId != other.proposalId) return false

        return true
    }

    override fun hashCode(): Int {
        return proposalId.hashCode()
    }
}