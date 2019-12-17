package byeduck.lunchroom.room.controller.request

import byeduck.lunchroom.room.service.Deadlines
import byeduck.lunchroom.validators.constraints.DeadlinesConstraint
import javax.validation.constraints.NotBlank

data class CreateRoomRequest(
        @field:NotBlank
        val name: String,
        @field:NotBlank
        val ownerId: String,
        @DeadlinesConstraint
        val deadlines: Deadlines
)