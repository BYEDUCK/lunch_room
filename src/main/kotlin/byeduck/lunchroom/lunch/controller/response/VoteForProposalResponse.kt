package byeduck.lunchroom.lunch.controller.response

import byeduck.lunchroom.domain.LunchProposal

data class VoteForProposalResponse(
        val proposalId: String,
        val ratingSum: Int,
        val votesCount: Int
) {
    companion object {
        fun fromLunchProposal(lunchProposal: LunchProposal): VoteForProposalResponse {
            return VoteForProposalResponse(lunchProposal.id!!, lunchProposal.ratingSum, lunchProposal.votesCount)
        }
    }
}