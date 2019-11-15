package byeduck.lunchroom.user.exceptions

class InvalidCredentialsException(val nick: String) : RuntimeException("Invalid credentials for user $nick")