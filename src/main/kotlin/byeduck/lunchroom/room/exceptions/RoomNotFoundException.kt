package byeduck.lunchroom.room.exceptions

import byeduck.lunchroom.error.exceptions.ResourceNotFoundException

class RoomNotFoundException(identifier: String) : ResourceNotFoundException("Room", identifier)