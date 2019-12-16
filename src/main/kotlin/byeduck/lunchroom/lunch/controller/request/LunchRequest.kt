package byeduck.lunchroom.lunch.controller.request

import byeduck.lunchroom.domain.MenuItem


data class LunchRequest(
        var lunchRequestType: LunchRequestType,
        var proposalId: String?,
        var roomId: String,
        var userId: String,
        var title: String?,
        var menuItems: List<MenuItem> = ArrayList(),
        var rating: Int?
)