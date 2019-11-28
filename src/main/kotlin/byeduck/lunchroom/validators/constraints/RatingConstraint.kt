package byeduck.lunchroom.validators.constraints

import byeduck.lunchroom.validators.RatingConstraintValidator
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@MustBeDocumented
@Constraint(validatedBy = [RatingConstraintValidator::class])
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class RatingConstraint(
        val message: String = "Rating not valid",
        val groups: Array<KClass<Any>> = [],
        val payload: Array<KClass<out Payload>> = []
)