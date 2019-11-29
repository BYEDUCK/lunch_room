package byeduck.lunchroom.lunch.controller

import byeduck.lunchroom.NICK_HEADER_NAME
import byeduck.lunchroom.TOKEN_HEADER_NAME
import byeduck.lunchroom.lunch.controller.request.CreateLunchProposalRequest
import byeduck.lunchroom.lunch.controller.request.VoteForProposalRequest
import byeduck.lunchroom.lunch.controller.response.CreateLunchProposalResponse
import byeduck.lunchroom.lunch.controller.response.LunchProposalResponse
import byeduck.lunchroom.lunch.controller.response.VoteForProposalResponse
import byeduck.lunchroom.lunch.service.LunchService
import byeduck.lunchroom.token.ValidateToken
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping(value = ["/lunch"], headers = [NICK_HEADER_NAME, TOKEN_HEADER_NAME])
class LunchController(
        @Autowired
        private val lunchService: LunchService
) {

    @PostMapping(value = [""], consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    @ValidateToken
    fun addLunchProposal(
            @Valid @RequestBody request: CreateLunchProposalRequest, @RequestHeader requestHeaders: HttpHeaders
    ): CreateLunchProposalResponse {
        return CreateLunchProposalResponse.fromLunchProposal(
                lunchService.addLunchProposal(request.userId, request.roomId, request.title, request.menuItems)
        )
    }

    @PostMapping(value = ["vote"], consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ValidateToken
    fun voteForProposal(
            @Valid @RequestBody request: VoteForProposalRequest, @RequestHeader requestHeaders: HttpHeaders
    ): VoteForProposalResponse {
        return VoteForProposalResponse.fromLunchProposal(
                lunchService.voteForProposal(request.userId, request.roomId, request.proposalId, request.rating)
        )
    }

    @GetMapping(value = ["/{id}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ValidateToken
    fun findByRoomId(
            @PathVariable("id") roomId: String,
            @RequestParam("userId") userId: String,
            @RequestHeader headers: HttpHeaders
    ): List<LunchProposalResponse> {
        return lunchService.findAllByRoomId(userId, roomId).map { LunchProposalResponse.fromLunchProposal(it) }
    }

}