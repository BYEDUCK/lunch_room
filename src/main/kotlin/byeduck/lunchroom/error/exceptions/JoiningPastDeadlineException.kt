package byeduck.lunchroom.error.exceptions

class JoiningPastDeadlineException(deadlineDate: String) : IllegalArgumentException("You tried to join room after sign deadline: $deadlineDate")