package byeduck.lunchroom.room.controller.response

import byeduck.lunchroom.room.service.Summary

data class SummaryResponse(
        val summaries: List<Summary>
)