package byeduck.lunchroom.room.controller

import byeduck.lunchroom.room.service.Deadlines
import byeduck.lunchroom.room.validator.DeadlinesConstraint
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

data class RoomCreateRequest(
        @NotEmpty
        @NotBlank
        val name: String,
        @NotEmpty
        @NotBlank
        val ownerId: String,
        @DeadlinesConstraint
        val deadlines: Deadlines
)