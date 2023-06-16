package team.aliens.domain.exception

sealed class StudyRoomException(
    message: String,
    val code: Int,
) : RuntimeException(message) {

    object GenderGradeMismatch : StudyRoomException(
        message = "Gender and Grade mismatch",
        code = 401,
    )

    object NotApplicationTime : StudyRoomException(
        message = "Not application time",
        code = 403,
    )

    object SeatNotFound : StudyRoomException(
        message = "Seat not found",
        code = 404,
    )

    object AvailableTimeNotFound : StudyRoomException(
        message = "Available time not found",
        code = 404,
    )

    object SomeoneAlreadyApplied : StudyRoomException(
        message = "Someone already applied",
        code = 409,
    )
}