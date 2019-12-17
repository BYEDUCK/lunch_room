package byeduck.lunchroom.room.controller.request

import javax.validation.constraints.NotBlank


data class JoinRoomByIdRequest(
        @field:NotBlank
        var userId: String,
        @field:NotBlank
        var roomId: String
)