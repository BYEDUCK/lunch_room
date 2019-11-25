package byeduck.lunchroom.user.controller

import byeduck.lunchroom.validators.constraints.PasswordConstraint
import byeduck.lunchroom.validators.constraints.SimpleStringConstraint

data class SignRequest(
        @SimpleStringConstraint
        val nick: String,
        @PasswordConstraint
        val password: String
)