package team.aliens.dms.android.shared.validator.test

import org.junit.Test
import team.aliens.dms.android.shared.validator.PasswordValidator
import team.aliens.dms.android.shared.validator.checkIfPasswordValid

class PasswordValidatorTest {

    @Test
    fun `Test empty password`() {
        val emptyPassword = ""

        assert(!PasswordValidator.validate(emptyPassword))
        assert(!checkIfPasswordValid(emptyPassword))
    }

    @Test
    fun `Test password with only english digits`() {
        val englishOnlyPassword = "abcdefgh"

        assert(!PasswordValidator.validate(englishOnlyPassword))
        assert(!checkIfPasswordValid(englishOnlyPassword))
    }

    @Test
    fun `Test password with only numbers`() {
        val numbersOnlyPassword = "12345678"

        assert(!PasswordValidator.validate(numbersOnlyPassword))
        assert(!checkIfPasswordValid(numbersOnlyPassword))
    }

    @Test
    fun `Test password under length 8`() {
        val passwordUnderLength8 = "abc123!"

        assert(!PasswordValidator.validate(passwordUnderLength8))
        assert(!checkIfPasswordValid(passwordUnderLength8))
    }

    @Test
    fun `Test password over length 20`() {
        val passwordOverLength20 =
            "abcd1234" + "abcd1234" + "!!.." + "-"

        assert(!PasswordValidator.validate(passwordOverLength20))
        assert(!checkIfPasswordValid(passwordOverLength20))
    }

    @Test
    fun `Test password containing blank`() {
        val passwordWithBlank = "ab d1234!"

        assert(!PasswordValidator.validate(passwordWithBlank))
        assert(!checkIfPasswordValid(passwordWithBlank))
    }
}
