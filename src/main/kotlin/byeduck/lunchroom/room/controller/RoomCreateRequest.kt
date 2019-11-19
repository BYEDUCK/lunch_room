package byeduck.lunchroom.room.controller

data class RoomCreateRequest(
        val name: String,
        val ownerId: String,
        val signDeadline: Long,
        val postDeadline: Long,
        val priorityDeadline: Long
)