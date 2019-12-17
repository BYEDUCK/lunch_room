package byeduck.lunchroom.room.controller.request

import javax.validation.constraints.NotBlank


class JoinRoomByNameRequest(
        @field:NotBlank
        var userId: String,
        @field:NotBlank
        var roomName: String
)