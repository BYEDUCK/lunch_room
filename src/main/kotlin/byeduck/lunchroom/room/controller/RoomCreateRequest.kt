package byeduck.lunchroom.room.controller

data class RoomCreateRequest(
        val name: String,
        val ownerNick: String,
        val signDeadline: Long,
        val postDeadline: Long,
        val priorityDeadline: Long
)