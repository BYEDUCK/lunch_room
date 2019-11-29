package byeduck.lunchroom.lunch.controller.response

import byeduck.lunchroom.domain.LunchProposal
import byeduck.lunchroom.domain.MenuItem

data class CreateLunchProposalResponse(
        val proposalId: String,
        val title: String,
        val menuItems: List<MenuItem>,
        val ratingSum: Int = 0,
        val votesCount: Int = 0
) {
    companion object {
        fun fromLunchProposal(lunchProposal: LunchProposal): CreateLunchProposalResponse {
            return CreateLunchProposalResponse(lunchProposal.id!!, lunchProposal.title, lunchProposal.menuItems)
        }
    }
}