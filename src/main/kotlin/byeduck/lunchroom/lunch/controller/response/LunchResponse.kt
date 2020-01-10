package byeduck.lunchroom.lunch.controller.response

data class LunchResponse(
        val total: Int,
        val proposals: List<LunchProposalDTO> = ArrayList()
)