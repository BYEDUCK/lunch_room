package byeduck.lunchroom.error.exceptions

class RequiredParameterEmptyException(name: String) : IllegalArgumentException("Required parameter $name is empty")