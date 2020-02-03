package byeduck.lunchroom.domain

import org.springframework.data.annotation.Id

data class Room(
        @Id
        var id: String?,
        var name: String,
        var owner: String,
        var initialDeadline: Long,
        var voteDeadline: Long,
        var users: MutableList<RoomUser>,
        var open: Boolean = true
) {
    constructor(name: String, owner: String, initialDeadline: Long, voteDeadline: Long)
            : this(null, name, owner, initialDeadline, voteDeadline, emptyList<RoomUser>().toMutableList())
}