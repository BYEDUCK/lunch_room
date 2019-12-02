package byeduck.lunchroom.room.controller.request

import byeduck.lunchroom.validators.constraints.NotBlank

data class JoinRoomByIdRequest(
        @NotBlank
        var userId: String,
        @NotBlank
        var roomId: String
)