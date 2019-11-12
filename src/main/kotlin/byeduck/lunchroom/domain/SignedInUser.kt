package byeduck.lunchroom.domain

import com.fasterxml.jackson.annotation.JsonUnwrapped

data class SignedInUser(
        @JsonUnwrapped
        val user: User,
        val token: String
)