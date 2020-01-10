package byeduck.lunchroom.lunch.controller.response

import byeduck.lunchroom.domain.LunchProposal
import byeduck.lunchroom.domain.MenuItem

data class LunchProposalDTO(
        val proposalId: String,
        val title: String,
        val menuUrl: String,
        val menuItems: List<MenuItem>,
        val proposalOwnerId: String,
        val ratingSum: Int,
        val votesCount: Int
) {
    companion object {
        fun fromLunchProposal(lunchProposal: LunchProposal): LunchProposalDTO {
            return LunchProposalDTO(
                    lunchProposal.id!!, lunchProposal.title, lunchProposal.menuUrl, lunchProposal.menuItems,
                    lunchProposal.proposalOwnerId, lunchProposal.ratingSum, lunchProposal.votesCount
            )
        }
    }
}