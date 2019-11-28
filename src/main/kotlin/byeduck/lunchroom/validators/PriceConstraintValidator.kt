package byeduck.lunchroom.validators

import byeduck.lunchroom.validators.constraints.PriceConstraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class PriceConstraintValidator : ConstraintValidator<PriceConstraint, Double> {
    override fun isValid(price: Double?, p1: ConstraintValidatorContext?): Boolean {
        return price != null && price > 0.0
    }
}