package team.aliens.domain.exception

sealed class StudyRoomException(
    message: String,
    val code: Int,
) : RuntimeException(message) {

    object StudyRoomGenderGradeMismatch : StudyRoomException(
        message = "Gender and Grade mismatch",
        code = 401,
    )

    object NotStudyRoomApplicationTime : StudyRoomException(
        message = "Not study room application time",
        code = 403,
    )

    object StudyRoomSeatNotFound : StudyRoomException(
        message = "Seat not found",
        code = 404,
    )

    object StudyRoomAvailableTimeNotFound : StudyRoomException(
        message = "Available time not found",
        code = 404,
    )

    object StudyRoomSomeoneAlreadyApplied : StudyRoomException(
        message = "Someone already applied",
        code = 409,
    )
}
