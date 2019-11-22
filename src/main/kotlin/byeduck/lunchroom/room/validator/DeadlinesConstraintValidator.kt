package byeduck.lunchroom.room.validator

import byeduck.lunchroom.room.service.Deadlines
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class DeadlinesConstraintValidator : ConstraintValidator<DeadlinesConstraint, Deadlines> {
    override fun isValid(deadlines: Deadlines?, p1: ConstraintValidatorContext?): Boolean {
        return areDeadlinesValid(deadlines)
    }

    private fun areDeadlinesValid(deadlines: Deadlines?): Boolean {
        return deadlines != null
                && (deadlines.signDeadline > System.currentTimeMillis())
                && ((deadlines.signDeadline < deadlines.postDeadline) && (deadlines.postDeadline < deadlines.priorityDeadline))
    }

}
