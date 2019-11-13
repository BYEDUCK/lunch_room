package byeduck.lunchroom.user.exceptions

class UserNotFoundException(nick: String) : ResourceNotFoundException("User", nick)