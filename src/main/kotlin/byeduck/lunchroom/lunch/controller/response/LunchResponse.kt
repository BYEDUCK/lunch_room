package byeduck.lunchroom.lunch.controller.response

import byeduck.lunchroom.domain.LunchProposal
import byeduck.lunchroom.domain.MenuItem

data class LunchResponse(
        val proposalId: String,
        val title: String,
        val menuItems: List<MenuItem>,
        val ratingSum: Int,
        val votesCount: Int
) {
    companion object {
        fun fromLunchProposal(lunchProposal: LunchProposal): LunchResponse {
            return LunchResponse(
                    lunchProposal.id!!, lunchProposal.title, lunchProposal.menuItems, lunchProposal.ratingSum, lunchProposal.votesCount
            )
        }
    }
}