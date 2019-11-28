package byeduck.lunchroom.validators

import byeduck.lunchroom.validators.constraints.NotEmptyList
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class NotEmptyListConstraintValidator : ConstraintValidator<NotEmptyList, MutableList<Any>> {
    override fun isValid(list: MutableList<Any>?, p1: ConstraintValidatorContext?): Boolean {
        return list != null && list.isNotEmpty()
    }
}