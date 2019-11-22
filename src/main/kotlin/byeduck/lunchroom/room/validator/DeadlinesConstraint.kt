package byeduck.lunchroom.room.validator

import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@MustBeDocumented
@Constraint(validatedBy = [DeadlinesConstraintValidator::class])
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class DeadlinesConstraint(
        val message: String = "Deadlines are not valid",
        val groups: Array<KClass<Any>> = [],
        val payload: Array<KClass<out Payload>> = []
)