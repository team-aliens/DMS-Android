package team.aliens.dms.android.shared.validator

object AsciiValidator : Validator<String>() {
    override val regex = Regex("^[a-zA-Z0-9]*\$")

    override fun validate(value: String): Boolean = value.matches(regex)
}

fun checkIfAscii(value: String): Boolean = AsciiValidator.validate(value)

val String.isAscii: Boolean
    inline get() = AsciiValidator.validate(this)
