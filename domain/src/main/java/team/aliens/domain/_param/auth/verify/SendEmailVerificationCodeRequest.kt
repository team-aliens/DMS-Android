package team.aliens.domain._param.auth.verify

data class SendEmailVerificationCodeRequest(
    val email: String,
    val type: SendEmailVerificationCodeType,
) {
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
