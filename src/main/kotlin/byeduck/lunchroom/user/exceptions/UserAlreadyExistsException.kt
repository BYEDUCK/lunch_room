package byeduck.lunchroom.user.exceptions

import byeduck.lunchroom.error.exceptions.ResourceAlreadyExistsException

class UserAlreadyExistsException : ResourceAlreadyExistsException("User")