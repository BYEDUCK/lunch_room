package byeduck.lunchroom.error

import byeduck.lunchroom.ErrorCodes
import byeduck.lunchroom.error.exceptions.*
import byeduck.lunchroom.user.exceptions.InvalidCredentialsException
import io.jsonwebtoken.JwtException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotWritableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import javax.validation.ConstraintViolationException

@ControllerAdvice
class ExceptionResolver {

    private val logger: Logger = LoggerFactory.getLogger(ExceptionResolver::class.java)

    @ExceptionHandler(value = [InvalidCredentialsException::class])
    fun handleInvalidCredentials(exception: Exception, request: WebRequest): ResponseEntity<ErrorMessage> {
        logger.error("Invalid credentials for user {}", (exception as InvalidCredentialsException).nick)
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ErrorMessage(ErrorCodes.INVALID_CREDENTIALS, exception.message))
    }

    @ExceptionHandler(value = [InvalidTokenException::class, JwtException::class])
    fun handleInvalidToken(exception: Exception, request: WebRequest): ResponseEntity<ErrorMessage> {
        logger.error("Invalid JWT token: {}", exception.message)
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ErrorMessage(ErrorCodes.INVALID_TOKEN, ""))
    }

    @ExceptionHandler(value = [IllegalArgumentException::class, RuntimeException::class])
    fun handleBadRequest(exception: Exception, request: WebRequest): ResponseEntity<ErrorMessage> {
        logger.error("BAD REQUEST: {}", exception.message)
        var errorCode = ErrorCodes.GENERAL
        when (exception) {
            is ResourceAlreadyExistsException -> errorCode = ErrorCodes.RESOURCE_ALREADY_EXISTS
            is ResourceNotFoundException -> errorCode = ErrorCodes.RESOURCE_NOT_FOUND
            is NotEnoughPointsAvailableException -> errorCode = ErrorCodes.NOT_ENOUGH_POINTS
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorMessage(errorCode, exception.message))
    }

    @ExceptionHandler(value = [UnauthorizedException::class])
    fun handleUnauthorizedRequest(exception: Exception, request: WebRequest): ResponseEntity<ErrorMessage> {
        logger.error("UNAUTHORIZED: {}", exception.message)
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ErrorMessage(ErrorCodes.UNAUTHORIZED, exception.message))
    }

    @ExceptionHandler(value = [ConstraintViolationException::class, MethodArgumentNotValidException::class])
    fun handleConstraintViolation(exception: Exception, request: WebRequest): ResponseEntity<ErrorMessage> {
        logger.error("CONSTRAINT VIOLATION: {}", exception.message)
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorMessage(ErrorCodes.CONSTRAINT_VIOLATION, parseConstraintViolationMsg(exception.message)))
    }

    @ExceptionHandler(value = [Exception::class])
    fun handleOtherExceptions(exception: Exception, request: WebRequest): ResponseEntity<ErrorMessage> {
        logger.error("UNEXPECTED EXCEPTION: {}", exception.message)
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorMessage(ErrorCodes.GENERAL, exception.message))
    }

    @ExceptionHandler(value = [HttpMessageNotWritableException::class])
    fun handleUnsupportedContentType(exception: Exception, request: WebRequest): ResponseEntity<ErrorMessage> {
        logger.error("HTTP MSG NOT WRITABLE: {}", exception.message)
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorMessage(ErrorCodes.GENERAL, exception.message))
    }

    private fun parseConstraintViolationMsg(msg: String?) = if (msg != null) {
        val idx = msg.lastIndexOf("default message [")
        if (idx > 0) {
            msg.substring(idx + 17, msg.length - 3)
        } else msg
    } else ""

}