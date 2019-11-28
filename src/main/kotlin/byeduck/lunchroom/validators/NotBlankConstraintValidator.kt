package byeduck.lunchroom.validators

import byeduck.lunchroom.validators.constraints.NotBlank
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class NotBlankConstraintValidator : ConstraintValidator<NotBlank, String> {
    override fun isValid(s: String?, p1: ConstraintValidatorContext?): Boolean {
        return s != null && s.isNotEmpty() && s.isNotBlank()
    }
}