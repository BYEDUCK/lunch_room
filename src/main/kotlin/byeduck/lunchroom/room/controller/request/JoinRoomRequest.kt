package byeduck.lunchroom.room.controller.request

import byeduck.lunchroom.validators.constraints.SimpleStringConstraint

data class JoinRoomRequest(
        @SimpleStringConstraint
        var userId: String,
        @SimpleStringConstraint
        var roomName: String
)