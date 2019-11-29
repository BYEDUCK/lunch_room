package byeduck.lunchroom.domain

data class RoomUser(
        var user: User,
        var points: Int,
        var votes: MutableList<Vote>
) {

    constructor(user: User) : this(user, -1, ArrayList())

    constructor(user: User, startingPoints: Int) : this(user, startingPoints, ArrayList())

    // ignore points and votes for equality
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RoomUser) return false

        if (user != other.user) return false

        return true
    }

    override fun hashCode(): Int {
        return user.hashCode()
    }
}