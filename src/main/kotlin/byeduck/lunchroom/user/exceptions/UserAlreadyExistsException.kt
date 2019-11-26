package byeduck.lunchroom.user.exceptions

import byeduck.lunchroom.error.exceptions.ResourceAlreadyExistsException

class UserAlreadyExistsException(nick: String) : ResourceAlreadyExistsException("User", nick)