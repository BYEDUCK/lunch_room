package byeduck.lunchroom.room.exceptions

import byeduck.lunchroom.error.exceptions.ResourceNotFoundException

class RoomNotFoundException(name: String) : ResourceNotFoundException("Room", name)