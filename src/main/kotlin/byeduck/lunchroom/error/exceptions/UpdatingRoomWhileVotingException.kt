package byeduck.lunchroom.error.exceptions

class UpdatingRoomWhileVotingException(voteDeadline: String)
    : RuntimeException("You tried to update room before voting end: $voteDeadline")