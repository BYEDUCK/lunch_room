package byeduck.lunchroom.domain

import org.springframework.data.annotation.Id

data class Room(
        @Id
        var id: String?,
        var name: String,
        var owner: String,
        var signDeadline: Long,
        var postDeadline: Long,
        var priorityDeadline: Long
) {
    constructor(name: String, owner: String, signDeadline: Long, postDeadline: Long, priorityDeadline: Long)
            : this(null, name, owner, signDeadline, postDeadline, priorityDeadline)
}