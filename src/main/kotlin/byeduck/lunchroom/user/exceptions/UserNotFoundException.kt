package byeduck.lunchroom.user.exceptions

import byeduck.lunchroom.error.exceptions.ResourceNotFoundException

class UserNotFoundException : ResourceNotFoundException("User")