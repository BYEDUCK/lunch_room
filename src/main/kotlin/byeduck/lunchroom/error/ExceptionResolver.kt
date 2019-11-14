package byeduck.lunchroom.error

import byeduck.lunchroom.error.exceptions.InvalidTokenException
import byeduck.lunchroom.error.exceptions.ResourceAlreadyExistsException
import byeduck.lunchroom.error.exceptions.ResourceNotFoundException
import byeduck.lunchroom.user.exceptions.InvalidCredentialsException
import byeduck.lunchroom.user.exceptions.UserAlreadyExistsException
import io.jsonwebtoken.MalformedJwtException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class ExceptionResolver {

    @ExceptionHandler(value = [InvalidCredentialsException::class])
    fun handleInvalidCredentials(exception: Exception, request: WebRequest): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exception.message)
    }

    @ExceptionHandler(value = [ResourceAlreadyExistsException::class])
    fun handleResourceCollision(exception: UserAlreadyExistsException, request: WebRequest): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.message)
    }

    @ExceptionHandler(value = [ResourceNotFoundException::class])
    fun handleResourceNotFound(exception: Exception, request: WebRequest): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.message)
    }

    @ExceptionHandler(value = [InvalidTokenException::class, MalformedJwtException::class])
    fun handleInvalidToken(exception: Exception, request: WebRequest): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exception.message)
    }

}