package team.aliens.dms.android.shared.validator

object EmailValidator : Validator<String>() {
    override val regex =
        Regex("^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}\$")

    override fun validate(value: String): Boolean = value.matches(regex)
}

fun checkIfEmailValid(value: String): Boolean = EmailValidator.validate(value)
