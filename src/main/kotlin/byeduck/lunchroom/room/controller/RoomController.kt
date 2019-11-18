package byeduck.lunchroom.room.controller

import byeduck.lunchroom.domain.Room
import byeduck.lunchroom.room.service.RoomService
import byeduck.lunchroom.token.ValidateToken
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.*

@RestController
class RoomController(
        @Autowired
        private val roomService: RoomService
) {

    @PostMapping("/rooms")
    @ValidateToken
    fun addRoom(
            @RequestBody request: RoomCreateRequest, @RequestHeader requestHeaders: HttpHeaders
    ): Room {
        return roomService.addRoom(
                request.name, request.ownerNick, request.signDeadline, request.postDeadline, request.priorityDeadline
        )
    }

    @GetMapping("/rooms")
    @ValidateToken
    fun findRoomsByUserId(
            @RequestParam("userId") userId: String, @RequestHeader requestHeaders: HttpHeaders
    ): List<Room> {
        return roomService.getRoomsByUserId(userId)
    }

}