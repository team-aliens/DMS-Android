package team.aliens.dms_android.domain.exception

sealed class SchoolException(
    message: String,
) : RuntimeException(message) {

    object SchoolVerificationQuestionNotCertified :
        team.aliens.dms_android.domain.exception.SchoolException("School verification question is not certified")

    object SchoolVerificationCodeMismatch : team.aliens.dms_android.domain.exception.SchoolException("School verification code mismatch")

    object SchoolNotFound : team.aliens.dms_android.domain.exception.SchoolException("School not found")
}
