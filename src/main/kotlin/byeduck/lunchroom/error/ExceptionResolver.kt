package byeduck.lunchroom.error

import byeduck.lunchroom.user.exceptions.InvalidCredentialsException
import byeduck.lunchroom.user.exceptions.ResourceNotFoundException
import byeduck.lunchroom.user.exceptions.UserAlreadyExistsException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class ExceptionResolver {

    @ExceptionHandler(value = [InvalidCredentialsException::class])
    fun handleInvalidCredentials(exception: InvalidCredentialsException, request: WebRequest): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exception.message)
    }

    @ExceptionHandler(value = [UserAlreadyExistsException::class])
    fun handleUserCollision(exception: UserAlreadyExistsException, request: WebRequest): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with given nick already exists.")
    }

    @ExceptionHandler(value = [ResourceNotFoundException::class])
    fun handleResourceNotFound(exception: ResourceNotFoundException, request: WebRequest): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.message)
    }

}