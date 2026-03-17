package team.aliens.dms.android.feature.resetpassword.model

internal sealed interface ResetPasswordTextFieldError {
    val message: String?

    data class InvalidEmailVerificationCode(
        override val message: String? = "인증코드가 일치하지 않아요.",
    ) : ResetPasswordTextFieldError

    data class EmailVerificationCodeTimeExpired(
        override val message: String? = "인증코드 입력시간이 만료되었어요.",
    ) : ResetPasswordTextFieldError

    data class InvalidPasswordFormat(
        override val message: String? = "비밀번호 형식이 일치하지 않아요.",
    ) : ResetPasswordTextFieldError

    data class PasswordMismatch(
        override val message: String? = "비밀번호가 일치하지 않아요.",
    ) : ResetPasswordTextFieldError

    data object None : ResetPasswordTextFieldError {
        override val message: String? = null
    }
}

internal fun ResetPasswordTextFieldError.isError(): Boolean = this !is ResetPasswordTextFieldError.None
