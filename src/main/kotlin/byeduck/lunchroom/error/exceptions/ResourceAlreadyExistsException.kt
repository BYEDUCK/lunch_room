package byeduck.lunchroom.error.exceptions

open class ResourceAlreadyExistsException(identifier: String) : RuntimeException("$identifier already exists")