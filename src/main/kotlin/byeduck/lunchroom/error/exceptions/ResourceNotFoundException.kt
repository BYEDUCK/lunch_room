package byeduck.lunchroom.error.exceptions

open class ResourceNotFoundException(name: String, identifier: String) : IllegalArgumentException("$name not found: $identifier")