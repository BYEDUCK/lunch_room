package byeduck.lunchroom.room.exceptions

import byeduck.lunchroom.error.exceptions.ResourceAlreadyExistsException

class RoomAlreadyExistsException(name: String) : ResourceAlreadyExistsException("Room", name)