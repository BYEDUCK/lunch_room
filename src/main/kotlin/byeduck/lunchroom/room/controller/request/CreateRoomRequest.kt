package byeduck.lunchroom.room.controller.request

import byeduck.lunchroom.CANNOT_BE_EMPTY_MSG
import byeduck.lunchroom.room.service.Deadlines
import byeduck.lunchroom.validators.constraints.DeadlinesConstraint
import javax.validation.constraints.NotBlank

data class CreateRoomRequest(
        @field:NotBlank(message = "Room name $CANNOT_BE_EMPTY_MSG")
        val name: String,
        @field:NotBlank(message = "Owner id $CANNOT_BE_EMPTY_MSG")
        val ownerId: String,
        @DeadlinesConstraint
        val deadlines: Deadlines
)