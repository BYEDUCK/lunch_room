package byeduck.lunchroom.validators

import byeduck.lunchroom.validators.constraints.SimpleStringConstraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class SimpleStringConstraintValidator : ConstraintValidator<SimpleStringConstraint, String> {
    override fun isValid(s: String?, p1: ConstraintValidatorContext?): Boolean {
        return s != null && s.isNotEmpty() && s.isNotBlank()
    }
}