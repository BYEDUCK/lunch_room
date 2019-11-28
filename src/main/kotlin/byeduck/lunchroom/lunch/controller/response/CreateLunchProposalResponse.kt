package byeduck.lunchroom.lunch.controller.response

import byeduck.lunchroom.domain.LunchProposal
import byeduck.lunchroom.domain.MenuItem

data class CreateLunchProposalResponse(
        val proposalId: String,
        val menuItems: MutableList<MenuItem>
) {
    companion object {
        fun fromLunchProposal(lunchProposal: LunchProposal): CreateLunchProposalResponse {
            return CreateLunchProposalResponse(lunchProposal.id!!, lunchProposal.menuItems)
        }
    }
}