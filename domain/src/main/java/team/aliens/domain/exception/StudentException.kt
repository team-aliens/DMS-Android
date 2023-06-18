package team.aliens.domain.exception

sealed class StudentException(
    message: String,
) : RuntimeException(message) {

    object EmailUnauthorized : StudentException("Email unauthorized")

    object StudentInformationMismatch : StudentException("Student information mismatch")

    object EmailVerificationCodeUnauthorized :
        StudentException("Email verification code unauthorized")

    object StudentNotFound : StudentException("Student not found")

    object AccountIdDuplicated : StudentException("Account ID duplicated")

    object StudentAlreadyExists : StudentException("Student already exists")

    object AlreadySignedUp : StudentException("Already signed up")
}
