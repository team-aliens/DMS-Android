package team.aliens.dms.android.domain._legacy.exception

sealed class StudentException(
    message: String,
) : RuntimeException(message) {

    object EmailUnauthorized : team.aliens.dms.android.domain._legacy.exception.StudentException("Email unauthorized")

    object StudentInformationMismatch : team.aliens.dms.android.domain._legacy.exception.StudentException("Student information mismatch")

    object EmailVerificationCodeUnauthorized :
        team.aliens.dms.android.domain._legacy.exception.StudentException("Email verification code unauthorized")

    object StudentNotFound : team.aliens.dms.android.domain._legacy.exception.StudentException("Student not found")

    object AccountIdDuplicated : team.aliens.dms.android.domain._legacy.exception.StudentException("Account ID duplicated")

    object StudentAlreadyExists : team.aliens.dms.android.domain._legacy.exception.StudentException("Student already exists")

    object AlreadySignedUp : team.aliens.dms.android.domain._legacy.exception.StudentException("Already signed up")
}
