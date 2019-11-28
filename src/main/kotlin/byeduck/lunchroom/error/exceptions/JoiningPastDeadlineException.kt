package byeduck.lunchroom.error.exceptions

class JoiningPastDeadlineException(deadlineDate: String)
    : RuntimeException("You tried to join room after sign deadline: $deadlineDate")