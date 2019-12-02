package byeduck.lunchroom.room.controller.request

import byeduck.lunchroom.validators.constraints.NotBlank

class JoinRoomByNameRequest(
        @NotBlank
        var userId: String,
        @NotBlank
        var roomName: String
)