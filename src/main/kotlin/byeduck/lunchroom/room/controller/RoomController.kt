package byeduck.lunchroom.room.controller

import byeduck.lunchroom.NICK_HEADER_NAME
import byeduck.lunchroom.TOKEN_HEADER_NAME
import byeduck.lunchroom.room.controller.request.*
import byeduck.lunchroom.room.controller.response.LotteryResponse
import byeduck.lunchroom.room.controller.response.RoomResponse
import byeduck.lunchroom.room.controller.response.SummaryResponse
import byeduck.lunchroom.room.service.RoomService
import byeduck.lunchroom.token.ValidateToken
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.NotBlank

@RestController
@RequestMapping(value = ["rooms"], headers = [NICK_HEADER_NAME, TOKEN_HEADER_NAME])
class RoomController(
        @Autowired
        private val roomService: RoomService,
        @Autowired
        private val msgTemplate: SimpMessagingTemplate
) {

    private val logger: Logger = LoggerFactory.getLogger(RoomController::class.java)

    @PostMapping(value = [""], consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    @ValidateToken
    fun addRoom(
            @Valid @RequestBody request: CreateRoomRequest, @RequestHeader requestHeaders: HttpHeaders,
            @RequestParam(name = "defaults", required = false, defaultValue = "false") defaults: Boolean = false
    ): RoomResponse {
        logger.info("Adding room \"{}\" by user {}", request.name, request.ownerId)
        return RoomResponse.fromRoom(
                roomService.addRoom(request.name, request.ownerId, request.deadlines, defaults)
        )
    }

    @GetMapping(value = [""], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ValidateToken
    fun findRoomsByUserId(
            @RequestParam("userId") userId: String, @RequestHeader requestHeaders: HttpHeaders
    ): List<RoomResponse> {
        return roomService.findRoomsByUserId(userId).map { RoomResponse.fromRoom(it) }
    }

    @GetMapping(value = ["search"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ValidateToken
    fun findRoomByName(
            @RequestParam("name") roomName: String, @RequestHeader requestHeaders: HttpHeaders
    ): RoomResponse {
        return RoomResponse.fromRoom(roomService.findRoomByName(roomName))
    }

    @PostMapping(value = ["join"], consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ValidateToken
    fun joinRoomById(
            @Valid @RequestBody request: JoinRoomByIdRequest,
            @RequestHeader requestHeaders: HttpHeaders
    ): RoomResponse {
        val room = roomService.joinRoomById(request.roomId, request.userId)
        logger.info("User {} joined room {}", request.userId, request.roomId)
        return RoomResponse.fromRoom(room)
    }

    @DeleteMapping(value = ["/{id}"])
    @ValidateToken
    fun deleteRoom(
            @RequestHeader requestHeaders: HttpHeaders,
            @RequestHeader(TOKEN_HEADER_NAME) token: String,
            @PathVariable("id") @NotBlank roomId: String
    ) {
        logger.info("Deleting room {}", roomId)
        roomService.deleteRoom(roomId, token)
    }

    @PutMapping(value = [""], consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun updateRoom(
            @Valid @RequestBody request: UpdateRoomRequest,
            @RequestHeader requestHeaders: HttpHeaders,
            @RequestHeader(TOKEN_HEADER_NAME) token: String
    ): RoomResponse {
        logger.info("Updating room {}", request.roomId)
        return RoomResponse.fromRoom(roomService.updateRoom(request.roomId, token, request.deadlines))
    }

    @PostMapping(value = ["random"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    @ValidateToken
    fun doTheLottery(
            @RequestHeader requestHeaders: HttpHeaders,
            @RequestHeader(TOKEN_HEADER_NAME) token: String,
            @RequestBody @Valid request: LotteryRequest
    ) {
        val lottery = roomService.doTheLottery(request.userId, request.roomId, token)
        logger.info("Lottery requested by user {} in room {}", request.userId, request.roomId)
        msgTemplate.convertAndSend(
                "/room/lottery/${request.roomId}", LotteryResponse(lottery.userNick, lottery.proposalId)
        )
    }

    @PostMapping(value = ["summary"], consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ValidateToken
    fun getSummaries(
            @RequestHeader requestHeaders: HttpHeaders,
            @RequestBody @Valid request: SummaryRequest
    ): SummaryResponse {
        logger.info("Summary request by user {} for room {}", request.userId, request.roomId)
        return SummaryResponse(roomService.getSummaries(request.userId, request.roomId))
    }
}