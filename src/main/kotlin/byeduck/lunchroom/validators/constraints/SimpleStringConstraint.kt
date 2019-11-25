package byeduck.lunchroom.validators.constraints

import byeduck.lunchroom.validators.SimpleStringConstraintValidator
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@MustBeDocumented
@Constraint(validatedBy = [SimpleStringConstraintValidator::class])
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class SimpleStringConstraint(
        val message: String = "String not valid",
        val groups: Array<KClass<Any>> = [],
        val payload: Array<KClass<out Payload>> = []
)