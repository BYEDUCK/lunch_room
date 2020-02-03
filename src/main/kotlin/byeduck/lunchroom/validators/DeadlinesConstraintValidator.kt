package byeduck.lunchroom.validators

import byeduck.lunchroom.room.service.Deadlines
import byeduck.lunchroom.validators.constraints.DeadlinesConstraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class DeadlinesConstraintValidator : ConstraintValidator<DeadlinesConstraint, Deadlines> {
    override fun isValid(deadlines: Deadlines?, p1: ConstraintValidatorContext?): Boolean {
        return areDeadlinesValid(deadlines)
    }

    private fun areDeadlinesValid(deadlines: Deadlines?): Boolean {
        return deadlines != null
                && (deadlines.initialDeadline > System.currentTimeMillis())
                && (deadlines.initialDeadline < deadlines.voteDeadline)
    }

}
