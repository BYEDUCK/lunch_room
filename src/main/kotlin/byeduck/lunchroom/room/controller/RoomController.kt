package byeduck.lunchroom.room.controller

import byeduck.lunchroom.room.service.RoomService
import byeduck.lunchroom.token.ValidateToken
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
class RoomController(
        @Autowired
        private val roomService: RoomService
) {

    @PostMapping("/room")
    @ValidateToken
    fun addRoom(@RequestBody request: RoomCreateRequest, @RequestHeader requestHeaders: HttpHeaders): String? {
        val added = roomService.addRoom(
                request.name, request.ownerNick, request.signDeadline, request.postDeadline, request.priorityDeadline
        )
        return added.id
    }

}