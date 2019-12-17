package byeduck.lunchroom.lunch.service

import byeduck.lunchroom.domain.LunchProposal
import byeduck.lunchroom.domain.MenuItem
import javax.validation.Valid
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

interface LunchService {
    fun addLunchProposal(
            @NotBlank userId: String,
            @NotBlank roomId: String,
            @NotBlank title: String,
            @Valid menuItems: List<MenuItem>
    ): LunchProposal

    fun voteForProposal(
            @NotBlank userId: String,
            @NotBlank roomId: String,
            @NotBlank proposalId: String,
            @Min(1) @Max(6) rating: Int
    ): LunchProposal

    fun findAllByRoomId(
            @NotBlank userId: String,
            @NotBlank roomId: String
    ): List<LunchProposal>
}