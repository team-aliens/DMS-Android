package team.aliens.domain.exception

sealed class SchoolException(
    message: String,
    val code: Int,
) : RuntimeException(message) {

    object SchoolVerificationQuestionNotCertified : SchoolException(
        message = "School verification question is not certified",
        code = 401,
    )

    object SchoolVerificationCodeMismatch : SchoolException(
        message = "School verification code mismatch",
        code = 401,
    )

    object SchoolNotFound : SchoolException(
        message = "School not found",
        code = 404,
    )
}
