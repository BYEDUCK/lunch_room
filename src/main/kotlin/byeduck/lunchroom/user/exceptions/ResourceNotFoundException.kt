package byeduck.lunchroom.user.exceptions

open class ResourceNotFoundException(
        private val resourceClassName: String,
        private val resourceIdentifier: String
) : RuntimeException("$resourceClassName \"$resourceIdentifier\" not found.")