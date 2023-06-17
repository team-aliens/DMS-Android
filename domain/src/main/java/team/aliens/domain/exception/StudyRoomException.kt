package team.aliens.domain.exception

sealed class StudyRoomException(
    message: String,
    val code: Int,
) : RuntimeException(message) {

    object StudyRoomGenderGradeMismatch : StudyRoomException(
        message = "Study room gender and grade mismatch",
        code = 401,
    )

    object NotStudyRoomApplicationTime : StudyRoomException(
        message = "Not study room application time",
        code = 403,
    )

    object StudyRoomSeatNotFound : StudyRoomException(
        message = "Study room seat not found",
        code = 404,
    )

    object StudyRoomAvailableTimeNotFound : StudyRoomException(
        message = "Study room available time not found",
        code = 404,
    )

    object StudyRoomSomeoneAlreadyApplied : StudyRoomException(
        message = "Study room someone already applied",
        code = 409,
    )
}
