package byeduck.lunchroom

class ErrorCodes {
    companion object {
        const val RESOURCE_NOT_FOUND = 10
        const val RESOURCE_ALREADY_EXISTS = 20
        const val INVALID_TOKEN = 30
        const val INVALID_CREDENTIALS = 40
        const val CONSTRAINT_VIOLATION = 50
        const val UNAUTHORIZED = 60
        const val NOT_ENOUGH_POINTS = 70
        const val GENERAL = 100
    }
}