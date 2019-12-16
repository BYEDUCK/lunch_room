package byeduck.lunchroom.lunch.controller

import byeduck.lunchroom.error.exceptions.RequiredParameterEmptyException
import byeduck.lunchroom.lunch.controller.request.LunchRequest
import byeduck.lunchroom.lunch.controller.request.LunchRequestType
import byeduck.lunchroom.lunch.controller.response.LunchProposalResponse
import byeduck.lunchroom.lunch.service.LunchService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller

@Controller
class LunchWsController(
        @Autowired
        private val lunchService: LunchService
) {

    private val logger: Logger = LoggerFactory.getLogger(LunchWsController::class.java)

    @MessageMapping("/propose")
    @SendTo("/room/proposals")
    fun handleLunchRequests(request: LunchRequest): List<LunchProposalResponse> {
        val processed: MutableList<LunchProposalResponse> = ArrayList()
        when (request.lunchRequestType) {
            LunchRequestType.ADD -> {
                logger.info("Adding new lunch proposal by user {} in room {}", request.userId, request.roomId)
                processed.add(LunchProposalResponse.fromLunchProposal(
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
                processed.add(LunchProposalResponse.fromLunchProposal(
                        lunchService.voteForProposal(
                                request.userId,
                                request.roomId,
                                request.proposalId ?: throw RequiredParameterEmptyException("proposal id"),
                                request.rating ?: throw RequiredParameterEmptyException("rating")
                        )))
            }
            LunchRequestType.FIND -> {
                logger.info("Finding all lunch proposal for user {} in room {}", request.userId, request.roomId)
                processed.addAll(lunchService.findAllByRoomId(
                        request.userId,
                        request.roomId
                ).map { LunchProposalResponse.fromLunchProposal(it) })
            }
        }
        return processed
    }

}