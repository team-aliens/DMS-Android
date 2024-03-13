package team.aliens.dms.android.shared.validator

object IdValidator : Validator<String>() {
    override val regex = Regex("^[A-Za-z0-9._-]{4,20}\$")

    override fun validate(value: String): Boolean = value.matches(regex)
}

fun checkIfIdValid(value: String): Boolean = IdValidator.validate(value)
