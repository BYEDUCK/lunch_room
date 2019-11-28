package byeduck.lunchroom.room.controller.request

import byeduck.lunchroom.validators.constraints.NotBlank

data class JoinRoomRequest(
        @NotBlank
        var userId: String,
        @NotBlank
        var roomName: String
)