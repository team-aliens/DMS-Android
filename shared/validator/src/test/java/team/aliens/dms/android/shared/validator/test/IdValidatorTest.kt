package team.aliens.dms.android.shared.validator.test

import org.junit.Test
import team.aliens.dms.android.shared.validator.IdValidator
import team.aliens.dms.android.shared.validator.checkIfIdValid

class IdValidatorTest {

    @Test
    fun `Test empty id`() {
        val emptyId = ""

        assert(!IdValidator.validate(emptyId))
        assert(!checkIfIdValid(emptyId))
    }

    @Test
    fun `Test id not in English`() {
        val idNotInEnglish = "박준수"

        assert(!IdValidator.validate(idNotInEnglish))
        assert(!checkIfIdValid(idNotInEnglish))
    }

    @Test
    fun `Test id with only english digits`() {
        val englishOnlyId = "abcdefgh"

        assert(IdValidator.validate(englishOnlyId))
        assert(checkIfIdValid(englishOnlyId))
    }

    @Test
    fun `Test id with only numbers`() {
        val numbersOnlyId = "12345678"

        assert(IdValidator.validate(numbersOnlyId))
        assert(checkIfIdValid(numbersOnlyId))
    }

    @Test
    fun `Test id under length 4`() {
        val idUnderLength4 = "ab1"

        assert(!IdValidator.validate(idUnderLength4))
        assert(!checkIfIdValid(idUnderLength4))
    }

    @Test
    fun `Test id over length 20`() {
        val idOverLength20 =
            "abcd1234" + "abcd1234" + "abcd" + "1"

        assert(!IdValidator.validate(idOverLength20))
        assert(!checkIfIdValid(idOverLength20))
    }

    @Test
    fun `Test id containing blank`() {
        val idWithBlank = "ab d1234!"

        assert(!IdValidator.validate(idWithBlank))
        assert(!checkIfIdValid(idWithBlank))
    }

    @Test
    fun `Test id containing not allowed spacial character`() {
        val idWithNotAllowedSpecialCharacter = "abcd1234!"

        assert(!IdValidator.validate(idWithNotAllowedSpecialCharacter))
        assert(!checkIfIdValid(idWithNotAllowedSpecialCharacter))
    }

    @Test
    fun `Test id containing with allowed special character`() {
        val baseId = "abcd1234"
        val case1 = "$baseId."
        val case2 = "${baseId}_"
        val case3 = "$baseId-"

        assert(IdValidator.validate(case1))
        assert(checkIfIdValid(case1))

        assert(IdValidator.validate(case2))
        assert(checkIfIdValid(case2))

        assert(IdValidator.validate(case3))
        assert(checkIfIdValid(case3))
    }
}
