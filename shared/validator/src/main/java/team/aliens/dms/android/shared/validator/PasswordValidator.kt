package team.aliens.dms.android.shared.validator

object PasswordValidator : Validator<String>() {

    override val regex: Regex =
        Regex("^.*(?=^.{8,20}\$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#\$%^&+=]).*\$")

    override fun validate(value: String): Boolean = value.matches(regex)
}

fun checkPasswordValid(value: String): Boolean {
    if (value.isEmpty()) {
        return false
    }
    return PasswordValidator.validate(value)
}
