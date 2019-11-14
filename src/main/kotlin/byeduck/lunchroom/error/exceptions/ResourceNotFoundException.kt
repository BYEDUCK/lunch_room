package byeduck.lunchroom.error.exceptions

open class ResourceNotFoundException(identifier: String) : RuntimeException("$identifier not found.")