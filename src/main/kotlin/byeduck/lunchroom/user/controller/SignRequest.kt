package byeduck.lunchroom.user.controller

import byeduck.lunchroom.CANNOT_BE_EMPTY_MSG
import byeduck.lunchroom.validators.constraints.PasswordConstraint
import javax.validation.constraints.NotBlank

data class SignRequest(
        @field:NotBlank(message = "Nick $CANNOT_BE_EMPTY_MSG")
        val nick: String,
        @PasswordConstraint
        val password: String
)