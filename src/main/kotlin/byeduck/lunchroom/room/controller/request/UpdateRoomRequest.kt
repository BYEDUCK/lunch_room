package byeduck.lunchroom.room.controller.request

import byeduck.lunchroom.room.service.Deadlines
import byeduck.lunchroom.validators.constraints.DeadlinesConstraint
import byeduck.lunchroom.validators.constraints.NotBlank

data class UpdateRoomRequest(
        @NotBlank
        val roomId: String,
        @DeadlinesConstraint
        val deadlines: Deadlines
)