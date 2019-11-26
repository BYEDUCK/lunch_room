package byeduck.lunchroom.error.exceptions

open class ResourceAlreadyExistsException(name: String, identifier: String) : IllegalArgumentException("$name already exists: $identifier")