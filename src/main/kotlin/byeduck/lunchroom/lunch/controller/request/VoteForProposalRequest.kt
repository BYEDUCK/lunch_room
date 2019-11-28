package byeduck.lunchroom.lunch.controller.request

import byeduck.lunchroom.validators.constraints.NotBlank
import byeduck.lunchroom.validators.constraints.RatingConstraint

data class VoteForProposalRequest(
        @NotBlank
        var userId: String,
        @NotBlank
        var roomId: String,
        @NotBlank
        var proposalId: String,
        @RatingConstraint
        var rating: Int
)