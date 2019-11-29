package byeduck.lunchroom.lunch.service

import byeduck.lunchroom.domain.LunchProposal
import byeduck.lunchroom.domain.MenuItem

interface LunchService {
    fun addLunchProposal(userId: String, roomId: String, title: String, menuItems: List<MenuItem>): LunchProposal
    fun voteForProposal(userId: String, roomId: String, proposalId: String, rating: Int): LunchProposal
    fun findAllByRoomId(userId: String, roomId: String): List<LunchProposal>
}