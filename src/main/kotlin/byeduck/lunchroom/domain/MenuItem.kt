package byeduck.lunchroom.domain

import byeduck.lunchroom.validators.constraints.NotBlank
import byeduck.lunchroom.validators.constraints.PriceConstraint

data class MenuItem(
        @NotBlank
        var description: String,
        @PriceConstraint
        var price: Double
)