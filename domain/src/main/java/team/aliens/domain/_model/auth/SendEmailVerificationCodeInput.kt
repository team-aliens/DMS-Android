package team.aliens.domain._model.auth

/**
 * @author junsuPark
 * A request when sending email verification code
 * @property email an email which the verification code will be sent
 * @property type a type of verification email
 */
data class SendEmailVerificationCodeInput(
    val email: String,
    val type: SendEmailVerificationCodeType,
) {

    /**
     * @author junsuPark
     * A enum class of sending email verification code request type
     * [value] actual string value of enum
     * [SIGNUP] type of sign up
     * [PASSWORD] type of password
     */
    enum class SendEmailVerificationCodeType(
        val value: String,
    ) {
        SIGNUP(
            "SIGNUP",
        ),

        PASSWORD(
            "PASSWORD",
        ),
        ;
    }
}
