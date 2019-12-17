package byeduck.lunchroom.domain

import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

data class MenuItem(
        @field:NotBlank
        var description: String,
        @field:Min(1)
        var price: Double
)