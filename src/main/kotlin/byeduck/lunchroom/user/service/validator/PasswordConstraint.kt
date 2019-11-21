package byeduck.lunchroom.user.service.validator

import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@MustBeDocumented
@Constraint(validatedBy = [PasswordConstraintValidator::class])
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class PasswordConstraint(
        val message: String = "Password not valid",
        val groups: Array<KClass<Any>> = [],
        val payload: Array<KClass<out Payload>> = []
)