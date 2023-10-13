package team.aliens.dms.android.domain.exception

sealed class SchoolException(
    message: String,
) : RuntimeException(message) {

    object SchoolVerificationQuestionNotCertified :
        SchoolException("School verification question is not certified")

    object SchoolVerificationCodeMismatch : SchoolException("School verification code mismatch")

    object SchoolNotFound : SchoolException("School not found")
}
