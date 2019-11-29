package byeduck.lunchroom.lunch.controller.response

import byeduck.lunchroom.domain.LunchProposal
import byeduck.lunchroom.domain.MenuItem

data class LunchProposalResponse(
        val proposalId: String,
        val title: String,
        val menuItems: List<MenuItem>,
        val ratingSum: Int,
        val votesCount: Int
) {
    companion object {
        fun fromLunchProposal(lunchProposal: LunchProposal): LunchProposalResponse {
            return LunchProposalResponse(
                    lunchProposal.id!!, lunchProposal.title, lunchProposal.menuItems, lunchProposal.ratingSum, lunchProposal.votesCount
            )
        }
    }
}