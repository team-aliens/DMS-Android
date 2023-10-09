package team.aliens.dms_android.domain.exception

sealed class StudentException(
    message: String,
) : RuntimeException(message) {

    object EmailUnauthorized : team.aliens.dms_android.domain.exception.StudentException("Email unauthorized")

    object StudentInformationMismatch : team.aliens.dms_android.domain.exception.StudentException("Student information mismatch")

    object EmailVerificationCodeUnauthorized :
        team.aliens.dms_android.domain.exception.StudentException("Email verification code unauthorized")

    object StudentNotFound : team.aliens.dms_android.domain.exception.StudentException("Student not found")

    object AccountIdDuplicated : team.aliens.dms_android.domain.exception.StudentException("Account ID duplicated")

    object StudentAlreadyExists : team.aliens.dms_android.domain.exception.StudentException("Student already exists")

    object AlreadySignedUp : team.aliens.dms_android.domain.exception.StudentException("Already signed up")
}
