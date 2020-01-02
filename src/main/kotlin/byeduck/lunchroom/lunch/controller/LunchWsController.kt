package byeduck.lunchroom.lunch.controller

import byeduck.lunchroom.error.exceptions.RequiredParameterEmptyException
import byeduck.lunchroom.lunch.controller.request.LunchRequest
import byeduck.lunchroom.lunch.controller.request.LunchRequestType
import byeduck.lunchroom.lunch.controller.response.LunchResponse
import byeduck.lunchroom.lunch.service.LunchService
import byeduck.lunchroom.room.service.RoomService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.MessageExceptionHandler
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller

@Controller
class LunchWsController(
        @Autowired
        private val lunchService: LunchService,
        @Autowired
        private val msgTemplate: SimpMessagingTemplate,
        @Autowired
        private val roomService: RoomService
) {

    private val logger: Logger = LoggerFactory.getLogger(LunchWsController::class.java)

    // TODO: return different responses depended on request type
    @MessageMapping("/propose")
    fun handleLunchRequests(request: LunchRequest) {
        val processed: MutableList<LunchResponse> = ArrayList()
        when (request.lunchRequestType) {
            LunchRequestType.ADD -> {
                logger.info("Adding new lunch proposal by user {} in room {}", request.userId, request.roomId)
                processed.add(LunchResponse.fromLunchProposal(
                        lunchService.addLunchProposal(
                                request.userId,
                                request.roomId,
                                request.title ?: throw RequiredParameterEmptyException("proposal title"),
                                request.menuItems
                        )))
            }
            LunchRequestType.VOTE -> {
                logger.info(
                        "Voting for lunch proposal {} by user {} with {} rating",
                        request.proposalId, request.userId, request.rating
                )
                processed.add(LunchResponse.fromLunchProposal(
                        lunchService.voteForProposal(
                                request.userId,
                                request.roomId,
                                request.proposalId ?: throw RequiredParameterEmptyException("proposal id"),
                                request.rating ?: throw RequiredParameterEmptyException("rating")
                        )))
                roomService.notifyRoomUsersAboutChange(request.roomId)
            }
            LunchRequestType.FIND -> {
                logger.info("Finding all lunch proposal for user {} in room {}", request.userId, request.roomId)
                processed.addAll(lunchService.findAllByRoomId(
                        request.userId,
                        request.roomId
                ).map { LunchResponse.fromLunchProposal(it) })
                roomService.notifyRoomUsersAboutChange(request.roomId)
            }
        }
        msgTemplate.convertAndSend("/room/proposals/${request.roomId}", processed)
    }

    @MessageExceptionHandler
    fun handleException(
            exception: Exception, @Header("simpSessionId") sessionId: String
    ) {
        msgTemplate.convertAndSendToUser(sessionId, "/gimme", exception.message!!)
    }

}