package byeduck.lunchroom.user.controller

import byeduck.lunchroom.validators.constraints.NotBlank
import byeduck.lunchroom.validators.constraints.PasswordConstraint

data class SignRequest(
        @NotBlank
        val nick: String,
        @PasswordConstraint
        val password: String
)