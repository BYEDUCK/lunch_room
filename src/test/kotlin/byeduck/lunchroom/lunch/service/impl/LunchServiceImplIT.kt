package byeduck.lunchroom.lunch.service.impl

import byeduck.lunchroom.domain.MenuItem
import byeduck.lunchroom.domain.Room
import byeduck.lunchroom.domain.User
import byeduck.lunchroom.domain.Vote
import byeduck.lunchroom.error.exceptions.AlreadyVotedException
import byeduck.lunchroom.error.exceptions.InvalidRoomException
import byeduck.lunchroom.error.exceptions.NotEnoughPointsAvailableException
import byeduck.lunchroom.lunch.service.LunchService
import byeduck.lunchroom.repositories.RoomsRepository
import byeduck.lunchroom.repositories.UsersRepository
import byeduck.lunchroom.room.service.Deadlines
import byeduck.lunchroom.room.service.RoomService
import byeduck.lunchroom.user.service.UserAuthenticationService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext

@SpringBootTest
internal class LunchServiceImplIT {

    @Autowired
    private lateinit var lunchService: LunchService

    @Autowired
    private lateinit var authenticationService: UserAuthenticationService

    @Autowired
    private lateinit var roomService: RoomService

    @Autowired
    private lateinit var usersRepository: UsersRepository

    @Autowired
    private lateinit var roomRepository: RoomsRepository

    @Value("\${user.start.points}")
    private lateinit var userStartingPoints: String

    private val userNick = "testNick"
    private val roomName = "testName"
    private val delay = 500
    private lateinit var user: User
    private lateinit var room: Room

    @BeforeEach
    internal fun setUp() {
        addTestData()
    }

    @Test
    @DirtiesContext
    @DisplayName("Simple lunch proposal addition")
    internal fun testAddLunchProposalValid() {
        waitDelay()
        val lunchProposal = lunchService.addLunchProposal(user.id!!, room.id!!, getTestMenuItems())

        assertEquals(0, lunchProposal.ratingSum)
        assertEquals(0, lunchProposal.votesCount)
        assertEquals(room.id, lunchProposal.roomId)
    }

    @Test
    @DirtiesContext
    @DisplayName("When user tries to add proposal for room their not in - exception should be thrown")
    internal fun testAddLunchProposalWhenUserNotInRoom() {
        val otherUserNick = "other"
        authenticationService.signUp(otherUserNick, "12345678")
        val otherUser = usersRepository.findByNick(otherUserNick).get()

        waitDelay()
        assertThrows<InvalidRoomException> { lunchService.addLunchProposal(otherUser.id!!, room.id!!, getTestMenuItems()) }
    }

    @Test
    @DirtiesContext
    @DisplayName("User should be able to add proposal to room after joining it")
    internal fun testAddLunchProposalAfterJoinTheRoom() {
        val otherUserNick = "other"
        authenticationService.signUp(otherUserNick, "12345678")
        val otherUser = usersRepository.findByNick(otherUserNick).get()
        roomService.joinRoom(roomName, otherUser.id!!)
        waitDelay()
        val lunchProposal = lunchService.addLunchProposal(otherUser.id!!, room.id!!, getTestMenuItems())

        assertEquals(0, lunchProposal.ratingSum)
        assertEquals(0, lunchProposal.votesCount)
        assertEquals(room.id, lunchProposal.roomId)
    }

    @Test
    @DirtiesContext
    @DisplayName("Voting for proposal - valid")
    internal fun testVoteForProposalValid() {
        waitDelay()
        val lunchProposal = lunchService.addLunchProposal(user.id!!, room.id!!, getTestMenuItems())
        waitDelay()
        val rating = 4
        val voted = lunchService.voteForProposal(user.id!!, room.id!!, lunchProposal.id!!, rating)
        val updatedRoom = roomRepository.findById(room.id!!).get()
        val updatedUser = usersRepository.findById(user.id!!).get()

        assertEquals(1, voted.votesCount)
        assertEquals(rating, voted.ratingSum)
        assertThat(updatedUser.votes).contains(Vote(lunchProposal.id!!, rating))

        val roomUser = updatedRoom.users.first { it.user.id == updatedUser.id }
        assertEquals(userStartingPoints.toInt() - rating, roomUser.points)
    }

    @Test
    @DirtiesContext
    @DisplayName("Voting for same proposal twice should result in exception")
    internal fun testVoteTwiceForSameProposal() {
        waitDelay()
        val lunchProposal = lunchService.addLunchProposal(user.id!!, room.id!!, getTestMenuItems())
        waitDelay()
        val rating = 4
        lunchService.voteForProposal(user.id!!, room.id!!, lunchProposal.id!!, rating)

        assertThrows<AlreadyVotedException> { lunchService.voteForProposal(user.id!!, room.id!!, lunchProposal.id!!, rating) }
    }

    @Test
    @DirtiesContext
    @DisplayName("Voting on more rating than available points should result in exception")
    internal fun testVoteWithNotEnoughPoints() {
        waitDelay()
        val lunchProposal = lunchService.addLunchProposal(user.id!!, room.id!!, getTestMenuItems())
        waitDelay()
        val rating = userStartingPoints.toInt() + 1

        assertThrows<NotEnoughPointsAvailableException> {
            lunchService.voteForProposal(user.id!!, room.id!!, lunchProposal.id!!, rating)
        }
    }

    private fun addTestData() {
        val user = addTestUser()
        val room = addTestRoom(user.id!!)
        this.room = room
        this.user = user
    }

    private fun addTestUser(): User {
        authenticationService.signUp(userNick, "12345678")
        return usersRepository.findByNick(userNick).get()
    }

    private fun addTestRoom(userId: String): Room {
        val now = System.currentTimeMillis()
        return roomService.addRoom(
                roomName, userId, Deadlines(now + delay, now + 2 * delay, now + 3 * delay)
        )
    }

    private fun waitDelay() {
        Thread.sleep((delay + 1).toLong())
    }

    private fun getTestMenuItems(): MutableList<MenuItem> {
        return listOf(MenuItem("item 1", 1.0), MenuItem("item 2", 2.0)).toMutableList()
    }

}