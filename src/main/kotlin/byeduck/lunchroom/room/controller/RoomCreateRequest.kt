package byeduck.lunchroom.room.controller

import byeduck.lunchroom.token.TokenRequest

data class RoomCreateRequest(
        val name: String,
        val ownerNick: String,
        val signDeadline: Long,
        val postDeadline: Long,
        val priorityDeadline: Long,
        val token: String
) : TokenRequest(ownerNick, token)