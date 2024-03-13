package team.aliens.dms.android.shared.validator.test

import org.junit.Test
import team.aliens.dms.android.shared.validator.IdValidator
import team.aliens.dms.android.shared.validator.checkIfIdValid

@Suppress("SimplifyBooleanWithConstants", "LocalVariableName", "ConvertToStringTemplate")
class IdValidatorTest {

    @Test
    fun `Test empty id`() {
        val `empty id` = ""

        assert(IdValidator.validate(`empty id`) == false)
        assert(checkIfIdValid(`empty id`) == false)
    }

    @Test
    fun `Test id not in English`() {
        val `id not in english` = "박준수"

        assert(IdValidator.validate(`id not in english`) == false)
        assert(checkIfIdValid(`id not in english`) == false)
    }

    @Test
    fun `Test id with only english digits`() {
        val `english only id` = "abcdefgh"

        assert(IdValidator.validate(`english only id`) == true)
        assert(checkIfIdValid(`english only id`) == true)
    }

    @Test
    fun `Test id with only numbers`() {
        val `numbers only id` = "12345678"

        assert(IdValidator.validate(`numbers only id`) == true)
        assert(checkIfIdValid(`numbers only id`) == true)
    }

    @Test
    fun `Test id under length 4`() {
        val `id under length 4` = "ab1"

        assert(IdValidator.validate(`id under length 4`) == false)
        assert(checkIfIdValid(`id under length 4`) == false)
    }

    @Test
    fun `Test id over length 20`() {
        val `id over length 20` =
            "abcd1234" + "abcd1234" + "abcd" + "1"

        assert(IdValidator.validate(`id over length 20`) == false)
        assert(checkIfIdValid(`id over length 20`) == false)
    }

    @Test
    fun `Test id containing blank`() {
        val `id with blank` = "ab d1234!"

        assert(IdValidator.validate(`id with blank`) == false)
        assert(checkIfIdValid(`id with blank`) == false)
    }

    @Test
    fun `Test id containing not allowed spacial character`() {
        val `id with not allowed special character` = "abcd1234!"

        assert(IdValidator.validate(`id with not allowed special character`) == false)
        assert(checkIfIdValid(`id with not allowed special character`) == false)
    }

    @Test
    fun `Test id containing with allowed special character`() {
        val `base id` = "abcd1234"
        val case1 = `base id` + '.'
        val case2 = `base id` + '_'
        val case3 = `base id` + '-'

        assert(IdValidator.validate(case1) == true)
        assert(checkIfIdValid(case1) == true)

        assert(IdValidator.validate(case2) == true)
        assert(checkIfIdValid(case2) == true)

        assert(IdValidator.validate(case3) == true)
        assert(checkIfIdValid(case3) == true)
    }
}
