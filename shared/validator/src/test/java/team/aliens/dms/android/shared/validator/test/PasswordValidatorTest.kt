package team.aliens.dms.android.shared.validator.test

import org.junit.Test
import team.aliens.dms.android.shared.validator.PasswordValidator
import team.aliens.dms.android.shared.validator.checkIfPasswordValid

@Suppress("SimplifyBooleanWithConstants", "LocalVariableName")
class PasswordValidatorTest {

    @Test
    fun `Test empty password`() {
        val `empty password` = ""

        assert(PasswordValidator.validate(`empty password`) == false)
        assert(checkIfPasswordValid(`empty password`) == false)
    }

    @Test
    fun `Test password with only english digits`() {
        val `english only password` = "abcdefgh"

        assert(PasswordValidator.validate(`english only password`) == false)
        assert(checkIfPasswordValid(`english only password`) == false)
    }

    @Test
    fun `Test password with only numbers`() {
        val `numbers only password` = "12345678"

        assert(PasswordValidator.validate(`numbers only password`) == false)
        assert(checkIfPasswordValid(`numbers only password`) == false)
    }

    @Test
    fun `Test password under length 8`() {
        val `password under length 8` = "abc123!"

        assert(PasswordValidator.validate(`password under length 8`) == false)
        assert(checkIfPasswordValid(`password under length 8`) == false)
    }

    @Test
    fun `Test password over length 20`() {
        val `password over length 20` =
            "abcd1234" + "abcd1234" + "!!.." + "-"

        assert(PasswordValidator.validate(`password over length 20`) == false)
        assert(checkIfPasswordValid(`password over length 20`) == false)
    }

    @Test
    fun `Test password containing blank`() {
        val `password with blank` = "ab d1234!"

        assert(PasswordValidator.validate(`password with blank`) == false)
        assert(checkIfPasswordValid(`password with blank`) == false)
    }
}
