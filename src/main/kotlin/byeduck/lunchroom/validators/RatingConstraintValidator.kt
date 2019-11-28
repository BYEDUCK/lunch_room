package byeduck.lunchroom.validators

import byeduck.lunchroom.validators.constraints.RatingConstraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class RatingConstraintValidator : ConstraintValidator<RatingConstraint, Int> {
    override fun isValid(rating: Int?, p1: ConstraintValidatorContext?): Boolean {
        return rating != null && rating > 0 && rating < 6
    }
}