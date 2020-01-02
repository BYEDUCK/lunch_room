package byeduck.lunchroom.domain

import org.springframework.data.annotation.Id

data class Room(
        @Id
        var id: String?,
        var name: String,
        var owner: String,
        var signDeadline: Long,
        var postDeadline: Long,
        var voteDeadline: Long,
        var users: MutableList<RoomUser>,
        var open: Boolean = true
) {
    constructor(name: String, owner: String, signDeadline: Long, postDeadline: Long, voteDeadline: Long)
            : this(null, name, owner, signDeadline, postDeadline, voteDeadline, emptyList<RoomUser>().toMutableList())
}