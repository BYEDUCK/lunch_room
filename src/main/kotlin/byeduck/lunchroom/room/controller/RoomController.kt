package byeduck.lunchroom.room.controller

import byeduck.lunchroom.TOKEN_COOKIE_NAME
import byeduck.lunchroom.USER_ID_COOKIE_NAME
import byeduck.lunchroom.room.controller.request.*
import byeduck.lunchroom.room.controller.response.LotteryResponse
import byeduck.lunchroom.room.controller.response.RoomResponse
import byeduck.lunchroom.room.controller.response.SummaryResponse
import byeduck.lunchroom.room.service.RoomService
import byeduck.lunchroom.token.ValidateToken
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid
import javax.validation.constraints.NotBlank

@RestController
@RequestMapping(value = ["rooms"])
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
            @Valid @RequestBody request: CreateRoomRequest, httpRequest: HttpServletRequest,
            @RequestParam(name = "defaults", required = false) defaults: Boolean = false,
            @CookieValue(USER_ID_COOKIE_NAME) userId: String
    ): RoomResponse {
        logger.info("Adding room \"{}\" by user {}", request.name, userId)
        return RoomResponse.fromRoom(
                roomService.addRoom(request.name, userId, request.deadlines, defaults)
        )
    }

    @GetMapping(value = [""], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ValidateToken
    fun findRoomsByUserId(
            @CookieValue(USER_ID_COOKIE_NAME) userId: String,
            httpRequest: HttpServletRequest
    ): List<RoomResponse> {
        return roomService.findRoomsByUserId(userId).map { RoomResponse.fromRoom(it) }
    }

    @GetMapping(value = ["search"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ValidateToken
    fun findRoomsByName(
            @RequestParam("name") roomName: String, httpRequest: HttpServletRequest
    ): List<RoomResponse> {
        return roomService.findRoomByNameLike(roomName).map { RoomResponse.fromRoom(it) }
    }

    @GetMapping(value = ["byName"])
    @ValidateToken
    fun getRoomByName(
            @RequestParam("name") roomName: String, httpRequest: HttpServletRequest
    ): RoomResponse {
        return RoomResponse.fromRoom(roomService.findRoomByName(roomName))
    }

    @PostMapping(value = ["join"], consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ValidateToken
    fun joinRoomById(
            @Valid @RequestBody request: JoinRoomByIdRequest,
            httpRequest: HttpServletRequest,
            @CookieValue(USER_ID_COOKIE_NAME) userId: String
    ): RoomResponse {
        val room = roomService.joinRoomById(request.roomId, userId)
        logger.info("User {} joined room {}", userId, request.roomId)
        return RoomResponse.fromRoom(room)
    }

    @PostMapping(value = ["leave"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    @ValidateToken
    fun leaveRoom(
            @Valid @RequestBody request: LeaveRoomRequest,
            httpRequest: HttpServletRequest,
            @CookieValue(USER_ID_COOKIE_NAME) userId: String
    ) {
        logger.info("User {} is leaving room {}", userId, request.roomId)
        roomService.leaveRoom(request.roomId, userId)
    }

    @DeleteMapping(value = ["/{id}"])
    @ValidateToken
    fun deleteRoom(
            httpRequest: HttpServletRequest,
            @CookieValue(TOKEN_COOKIE_NAME) token: String,
            @PathVariable("id") @NotBlank roomId: String
    ) {
        logger.info("Deleting room {}", roomId)
        roomService.deleteRoom(roomId, token)
    }

    @PutMapping(value = [""], consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun updateRoom(
            @Valid @RequestBody request: UpdateRoomRequest,
            httpRequest: HttpServletRequest,
            @CookieValue(TOKEN_COOKIE_NAME) token: String
    ): RoomResponse {
        logger.info("Updating room {}", request.roomId)
        return RoomResponse.fromRoom(roomService.updateRoom(request.roomId, token, request.deadlines))
    }

    @PostMapping(value = ["random"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    @ValidateToken
    fun doTheLottery(
            httpRequest: HttpServletRequest,
            @CookieValue(TOKEN_COOKIE_NAME) token: String,
            @CookieValue(USER_ID_COOKIE_NAME) userId: String,
            @RequestBody @Valid request: LotteryRequest
    ) {
        val lottery = roomService.doTheLottery(userId, request.roomId, token)
        logger.info("Lottery requested by user {} in room {}", userId, request.roomId)
        msgTemplate.convertAndSend(
                "/room/lottery/${request.roomId}", LotteryResponse(lottery.userNick, lottery.proposalId)
        )
    }

    @PostMapping(value = ["lucky_shot"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    @ValidateToken
    fun doTheLuckyShot(
            httpRequest: HttpServletRequest,
            @CookieValue(TOKEN_COOKIE_NAME) token: String,
            @CookieValue(USER_ID_COOKIE_NAME) userId: String,
            @RequestBody @Valid request: LotteryRequest
    ) {
        val lottery = roomService.doTheLuckyShot(userId, request.roomId, token)
        logger.info("Lucky shot triggered by user {} in room {}", userId, request.roomId)
        msgTemplate.convertAndSend(
                "/room/lottery/${request.roomId}", LotteryResponse(lottery.userNick, lottery.proposalId)
        )
    }

    @PostMapping(value = ["summary"], consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ValidateToken
    fun getSummaries(
            httpRequest: HttpServletRequest,
            @RequestBody @Valid request: SummaryRequest,
            @CookieValue(USER_ID_COOKIE_NAME) userId: String
    ): SummaryResponse {
        logger.info("Summary request by user {} for room {}", userId, request.roomId)
        return SummaryResponse(roomService.getSummaries(userId, request.roomId))
    }
}