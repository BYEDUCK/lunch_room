package byeduck.lunchroom.lunch.controller.request

import byeduck.lunchroom.domain.MenuItem
import byeduck.lunchroom.validators.constraints.NotBlank
import byeduck.lunchroom.validators.constraints.NotEmptyList

data class CreateLunchProposalRequest(
        @NotBlank
        var userId: String,
        @NotBlank
        var roomId: String,
        @NotBlank
        var title: String,
        @NotEmptyList
        var menuItems: List<MenuItem>
)