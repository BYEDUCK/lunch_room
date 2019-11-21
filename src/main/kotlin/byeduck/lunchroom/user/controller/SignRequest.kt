package byeduck.lunchroom.user.controller

import byeduck.lunchroom.user.service.validator.PasswordConstraint

data class SignRequest(
        val nick: String,
        @PasswordConstraint
        val password: String
)