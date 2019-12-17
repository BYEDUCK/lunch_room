package byeduck.lunchroom.domain

import byeduck.lunchroom.CANNOT_BE_EMPTY_MSG
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

data class MenuItem(
        @field:NotBlank(message = "Menu item's description $CANNOT_BE_EMPTY_MSG")
        var description: String,
        @field:Min(value = 1, message = "Price must be > 1")
        var price: Double
)