package team.aliens.dms.android.feature.signup.model

internal sealed interface SignUpTextFieldError {
    val message: String?

    data class InvalidEmailVerificationCode(
        override val message: String? = "인증코드가 일치하지 않아요.",
    ) : SignUpTextFieldError

    data class EmailVerificationCodeTimeExpired(
        override val message: String? = "인증코드 입력시간이 만료되었어요.",
    ) : SignUpTextFieldError

    data object None : SignUpTextFieldError {
        override val message: String? = null
    }
}

internal fun SignUpTextFieldError.isError(): Boolean = this !is SignUpTextFieldError.None
