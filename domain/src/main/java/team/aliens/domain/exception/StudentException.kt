package team.aliens.domain.exception

sealed class StudentException(
    message: String,
    val code: Int,
) : RuntimeException(message) {

    object EmailUnauthorized : StudentException(
        message = "Email unauthorized",
        code = 401,
    )

    object StudentInformationMismatch : StudentException(
        message = "Student information mismatch",
        code = 401,
    )

    object EmailVerificationCodeUnauthorized : StudentException(
        message = "Email verification code unauthorized",
        code = 401,
    )

    object StudentNotFound : StudentException(
        message = "Student not found",
        code = 404,
    )

    object AccountIdDuplicated : StudentException(
        message = "Account ID duplicated",
        code = 409,
    )

    object StudentAlreadyExists : StudentException(
        message = "Student already exists",
        code = 409,
    )

    object AlreadySignedUp : StudentException(
        message = "Already signed up",
        code = 409,
    )
}
