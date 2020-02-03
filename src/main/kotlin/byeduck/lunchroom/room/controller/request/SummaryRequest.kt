package byeduck.lunchroom.room.controller.request

import byeduck.lunchroom.ROOM_ID_CANNOT_BE_EMPTY_MSG
import javax.validation.constraints.NotBlank

data class SummaryRequest(
        @field:NotBlank(message = ROOM_ID_CANNOT_BE_EMPTY_MSG) var roomId: String
)