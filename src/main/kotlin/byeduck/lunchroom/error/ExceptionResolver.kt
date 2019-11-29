package byeduck.lunchroom.error

import byeduck.lunchroom.ErrorCodes
import byeduck.lunchroom.error.exceptions.*
import byeduck.lunchroom.user.exceptions.InvalidCredentialsException
import io.jsonwebtoken.JwtException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

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
        var errorCode = ErrorCodes.GENERAL
        when (exception) {
            is ResourceAlreadyExistsException -> errorCode = ErrorCodes.RESOURCE_ALREADY_EXISTS
            is ResourceNotFoundException -> errorCode = ErrorCodes.RESOURCE_NOT_FOUND
            is JoiningPastDeadlineException -> errorCode = ErrorCodes.PAST_DEADLINE
            is UpdatingRoomWhileVotingException -> errorCode = ErrorCodes.UPDATE_WHILE_VOTE
            is NotEnoughPointsAvailableException -> errorCode = ErrorCodes.NOT_ENOUGH_POINTS
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorMessage(errorCode, exception.message))
    }

    @ExceptionHandler(value = [UnauthorizedException::class])
    fun handleUnauthorizedRequest(exception: Exception, request: WebRequest): ResponseEntity<ErrorMessage> {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ErrorMessage(ErrorCodes.UNAUTHORIZED, exception.message))
    }

}