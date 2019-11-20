package byeduck.lunchroom.room.controller

import byeduck.lunchroom.NICK_HEADER_NAME
import byeduck.lunchroom.TOKEN_HEADER_NAME
import byeduck.lunchroom.domain.Room
import byeduck.lunchroom.room.service.RoomService
import byeduck.lunchroom.token.ValidateToken
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/rooms"], headers = [NICK_HEADER_NAME, TOKEN_HEADER_NAME])
class RoomController(
        @Autowired
        private val roomService: RoomService
) {

    @PostMapping(value = [""], consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    @ValidateToken
    fun addRoom(
            @RequestBody request: RoomCreateRequest, @RequestHeader requestHeaders: HttpHeaders
    ): Room {
        return roomService.addRoom(
                request.name, request.ownerId, request.signDeadline, request.postDeadline, request.priorityDeadline
        )
    }

    @GetMapping(value = [""], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ValidateToken
    fun findRoomsByUserId(
            @RequestParam("userId") userId: String, @RequestHeader requestHeaders: HttpHeaders
    ): List<Room> {
        return roomService.findRoomsByUserId(userId)
    }

}