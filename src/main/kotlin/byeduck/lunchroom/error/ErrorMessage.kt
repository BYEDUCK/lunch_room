package byeduck.lunchroom.error

import com.fasterxml.jackson.annotation.JsonProperty

data class ErrorMessage(
        @JsonProperty("errorCode")
        val code: Int,
        @JsonProperty("errorMsg")
        val message: String?
)