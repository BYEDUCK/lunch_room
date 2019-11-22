package byeduck.lunchroom.room.controller

import byeduck.lunchroom.room.service.Deadlines
import byeduck.lunchroom.room.validator.DeadlinesConstraint

data class RoomCreateRequest(
        val name: String,
        val ownerId: String,
        @DeadlinesConstraint
        val deadlines: Deadlines
)