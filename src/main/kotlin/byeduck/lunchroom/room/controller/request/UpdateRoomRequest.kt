package byeduck.lunchroom.room.controller.request

import byeduck.lunchroom.room.service.Deadlines
import byeduck.lunchroom.validators.constraints.DeadlinesConstraint
import javax.validation.constraints.NotBlank

data class UpdateRoomRequest(
        @field:NotBlank
        val roomId: String,
        @DeadlinesConstraint
        val deadlines: Deadlines
)