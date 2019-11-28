package byeduck.lunchroom.validators

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class NotBlankValidatorTest {

    private lateinit var validator: NotBlankConstraintValidator

    @BeforeEach
    internal fun setUp() {
        validator = NotBlankConstraintValidator()
    }

    @Test
    @DisplayName("Validator should return false with null string")
    internal fun testValidationWithNullString() {
        assertFalse(validator.isValid(null, null))
    }

    @Test
    @DisplayName("Validator should return false with empty string")
    internal fun testValidationWithEmptyString() {
        assertFalse(validator.isValid("", null))
    }

    @Test
    @DisplayName("Validator should return false with blank string")
    internal fun testValidationWithBlankString() {
        assertFalse(validator.isValid("  ", null))
    }

    @Test
    @DisplayName("Validator should return true with non-null,empty,blank string")
    internal fun testValidationWithValidString() {
        assertTrue(validator.isValid("abc", null))
    }
}