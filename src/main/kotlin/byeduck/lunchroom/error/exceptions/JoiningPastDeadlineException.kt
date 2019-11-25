package byeduck.lunchroom.error.exceptions

class JoiningPastDeadlineException : IllegalArgumentException("You tried to join room after sign deadline")