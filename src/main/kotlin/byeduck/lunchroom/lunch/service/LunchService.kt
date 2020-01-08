package byeduck.lunchroom.lunch.service

import byeduck.lunchroom.CANNOT_BE_EMPTY_MSG
import byeduck.lunchroom.ROOM_ID_CANNOT_BE_EMPTY_MSG
import byeduck.lunchroom.USER_ID_CANNOT_BE_EMPTY_MSG
import byeduck.lunchroom.domain.LunchProposal
import byeduck.lunchroom.domain.MenuItem
import org.hibernate.validator.constraints.URL
import javax.validation.Valid
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

interface LunchService {
    fun addLunchProposal(
            @NotBlank(message = USER_ID_CANNOT_BE_EMPTY_MSG) userId: String,
            @NotBlank(message = ROOM_ID_CANNOT_BE_EMPTY_MSG) roomId: String,
            @NotBlank(message = "Proposal's title $CANNOT_BE_EMPTY_MSG") title: String,
            @NotBlank(message = "Proposal's menuUrl $CANNOT_BE_EMPTY_MSG") @URL menuUrl: String,
            @Valid menuItems: List<MenuItem>
    ): LunchProposal

    fun voteForProposal(
            @NotBlank(message = USER_ID_CANNOT_BE_EMPTY_MSG) userId: String,
            @NotBlank(message = ROOM_ID_CANNOT_BE_EMPTY_MSG) roomId: String,
            @NotBlank(message = "Proposal id $CANNOT_BE_EMPTY_MSG") proposalId: String,
            @Min(value = 1, message = "Rating must be > 1") @Max(value = 6, message = "Rating must be < 6") rating: Int
    ): LunchProposal

    fun findAllByRoomId(
            @NotBlank(message = USER_ID_CANNOT_BE_EMPTY_MSG) userId: String,
            @NotBlank(message = ROOM_ID_CANNOT_BE_EMPTY_MSG) roomId: String
    ): List<LunchProposal>
}