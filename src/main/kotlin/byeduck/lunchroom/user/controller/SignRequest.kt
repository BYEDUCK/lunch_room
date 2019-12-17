package byeduck.lunchroom.user.controller

import byeduck.lunchroom.validators.constraints.PasswordConstraint
import javax.validation.constraints.NotBlank

data class SignRequest(
        @field:NotBlank
        val nick: String,
        @PasswordConstraint
        val password: String
)