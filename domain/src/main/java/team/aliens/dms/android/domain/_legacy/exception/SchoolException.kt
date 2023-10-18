package team.aliens.dms.android.domain._legacy.exception

sealed class SchoolException(
    message: String,
) : RuntimeException(message) {

    object SchoolVerificationQuestionNotCertified :
        team.aliens.dms.android.domain._legacy.exception.SchoolException("School verification question is not certified")

    object SchoolVerificationCodeMismatch : team.aliens.dms.android.domain._legacy.exception.SchoolException("School verification code mismatch")

    object SchoolNotFound : team.aliens.dms.android.domain._legacy.exception.SchoolException("School not found")
}
