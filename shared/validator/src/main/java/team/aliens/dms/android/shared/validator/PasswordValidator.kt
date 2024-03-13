package team.aliens.dms.android.shared.validator

object PasswordValidator : Validator<String>() {

    override val regex =
        Regex("(?=.*[a-z])(?=.*[0-9])(?=.*[!#\$%&'()*+,./:;<=>?@＼^_`{|}~-])[a-zA-Z0-9!#\$%&'()*+,./:;<=>?@＼^_`{|}~-]{8,20}\$")

    override fun validate(value: String): Boolean = value.matches(regex)
}

fun checkIfPasswordValid(value: String): Boolean = PasswordValidator.validate(value)
