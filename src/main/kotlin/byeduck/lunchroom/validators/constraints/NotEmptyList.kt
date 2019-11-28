package byeduck.lunchroom.validators.constraints

import javax.validation.Payload
import kotlin.reflect.KClass

annotation class NotEmptyList(
        val message: String = "List cannot be empty",
        val groups: Array<KClass<Any>> = [],
        val payload: Array<KClass<out Payload>> = []
)