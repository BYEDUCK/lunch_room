package byeduck.lunchroom.room.controller.request

import byeduck.lunchroom.room.service.Deadlines
import byeduck.lunchroom.validators.constraints.DeadlinesConstraint
import byeduck.lunchroom.validators.constraints.SimpleStringConstraint

data class UpdateRoomRequest(
        @SimpleStringConstraint
        val roomName: String,
        @DeadlinesConstraint
        val deadlines: Deadlines
)