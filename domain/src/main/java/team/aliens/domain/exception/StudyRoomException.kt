package team.aliens.domain.exception

sealed class StudyRoomException(
    message: String,
) : RuntimeException(message) {

    object StudyRoomGenderGradeMismatch : StudyRoomException("Study room gender and grade mismatch")

    object NotStudyRoomApplicationTime : StudyRoomException("Not study room application time")

    object StudyRoomSeatNotFound : StudyRoomException("Study room seat not found")

    object StudyRoomAvailableTimeNotFound :
        StudyRoomException("Study room available time not found")

    object StudyRoomSomeoneAlreadyApplied : StudyRoomException("Study room someone already applied")
}
