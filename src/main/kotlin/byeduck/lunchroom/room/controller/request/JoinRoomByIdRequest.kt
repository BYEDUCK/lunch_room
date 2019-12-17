package byeduck.lunchroom.room.controller.request

import byeduck.lunchroom.ROOM_ID_CANNOT_BE_EMPTY_MSG
import byeduck.lunchroom.USER_ID_CANNOT_BE_EMPTY_MSG
import javax.validation.constraints.NotBlank


data class JoinRoomByIdRequest(
        @field:NotBlank(message = USER_ID_CANNOT_BE_EMPTY_MSG)
        var userId: String,
        @field:NotBlank(message = ROOM_ID_CANNOT_BE_EMPTY_MSG)
        var roomId: String
)