package byeduck.lunchroom.room.controller.request

import byeduck.lunchroom.room.service.Deadlines
import byeduck.lunchroom.validators.constraints.DeadlinesConstraint
import byeduck.lunchroom.validators.constraints.NotBlank

data class CreateRoomRequest(
        @NotBlank
        val name: String,
        @NotBlank
        val ownerId: String,
        @DeadlinesConstraint
        val deadlines: Deadlines
)