package byeduck.lunchroom.lunch.controller.request

import byeduck.lunchroom.domain.MenuItem
import byeduck.lunchroom.validators.constraints.NotBlank
import byeduck.lunchroom.validators.constraints.NotEmptyList

data class CreateLunchProposalRequest(
        @NotBlank
        var userId: String,
        @NotBlank
        var roomId: String,
        @NotEmptyList
        var menuItems: MutableList<MenuItem>
)