package byeduck.lunchroom.room.controller.request

import byeduck.lunchroom.ROOM_ID_CANNOT_BE_EMPTY_MSG
import byeduck.lunchroom.room.service.Deadlines
import byeduck.lunchroom.validators.constraints.DeadlinesConstraint
import javax.validation.constraints.NotBlank

data class UpdateRoomRequest(
        @field:NotBlank(message = ROOM_ID_CANNOT_BE_EMPTY_MSG)
        val roomId: String,
        @DeadlinesConstraint
        val deadlines: Deadlines
)