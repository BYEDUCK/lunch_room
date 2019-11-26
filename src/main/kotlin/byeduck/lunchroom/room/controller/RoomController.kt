package byeduck.lunchroom.room.controller

import byeduck.lunchroom.NICK_HEADER_NAME
import byeduck.lunchroom.TOKEN_HEADER_NAME
import byeduck.lunchroom.room.controller.request.CreateRoomRequest
import byeduck.lunchroom.room.controller.request.JoinRoomRequest
import byeduck.lunchroom.room.controller.request.UpdateRoomRequest
import byeduck.lunchroom.room.controller.response.DetailRoomResponse
import byeduck.lunchroom.room.controller.response.SimpleRoomResponse
import byeduck.lunchroom.room.service.RoomService
import byeduck.lunchroom.token.ValidateToken
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

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
            @Valid @RequestBody request: CreateRoomRequest, @RequestHeader requestHeaders: HttpHeaders
    ): SimpleRoomResponse {
        return SimpleRoomResponse.fromRoom(roomService.addRoom(request.name, request.ownerId, request.deadlines))
    }

    @GetMapping(value = [""], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ValidateToken
    fun findRoomsByUserId(
            @RequestParam("userId") userId: String, @RequestHeader requestHeaders: HttpHeaders
    ): List<SimpleRoomResponse> {
        return roomService.findRoomsByUserId(userId).map { SimpleRoomResponse.fromRoom(it) }
    }

    @PostMapping(value = ["join"], consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ValidateToken
    fun joinRoom(
            @Valid @RequestBody request: JoinRoomRequest,
            @RequestHeader requestHeaders: HttpHeaders
    ): DetailRoomResponse {
        return DetailRoomResponse.fromRoom(roomService.joinRoom(request.roomName, request.userId))
    }

    @DeleteMapping(value = ["del/{id}"])
    @ValidateToken
    fun deleteRoom(
            @RequestHeader requestHeaders: HttpHeaders,
            @RequestHeader(TOKEN_HEADER_NAME) token: String,
            @PathVariable("id") roomId: String
    ) {
        roomService.deleteRoom(roomId, token)
    }

    @PutMapping(value = [""], consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun updateRoom(
            @Valid @RequestBody request: UpdateRoomRequest,
            @RequestHeader requestHeaders: HttpHeaders,
            @RequestHeader(TOKEN_HEADER_NAME) token: String
    ): SimpleRoomResponse {
        return SimpleRoomResponse.fromRoom(roomService.updateRoom(request.roomName, token, request.deadlines))
    }

}