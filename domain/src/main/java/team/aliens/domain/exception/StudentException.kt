package team.aliens.domain.exception

sealed class StudentException(
    message: String,
    val code: Int,
) : RuntimeException(message) {

    object EmailUnauthorized : StudentException(
        message = "Email unauthorized",
        code = 401,
    )

    object StudentInfoMismatch : StudentException(
        message = "Student Information mismatch",
        code = 401,
    )

    object AuthCodeUnAuthorized : StudentException(
        message = "Auth code unauthorized",
        code = 401,
    )

    object StudentNotFound : StudentException(
        message = "Student not found",
        code = 404,
    )

    object AccountIdAlreadyExists : StudentException(
        message = "Account ID already exists",
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
