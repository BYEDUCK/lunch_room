package byeduck.lunchroom.validators

import byeduck.lunchroom.validators.constraints.PasswordConstraint
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

@Component
class PasswordConstraintValidator(
        @Value("\${password.min.length:8}")
        private val passMinLength: Int
) : ConstraintValidator<PasswordConstraint, String> {
    override fun isValid(password: String?, p1: ConstraintValidatorContext?): Boolean {
        return !password.isNullOrEmpty() && isPasswordValid(password)
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length >= passMinLength
    }
}