package team.aliens.dms_android.domain.exception

sealed class StudyRoomException(
    message: String,
) : RuntimeException(message) {

    object StudyRoomGenderGradeMismatch : team.aliens.dms_android.domain.exception.StudyRoomException("Study room gender and grade mismatch")

    object NotStudyRoomApplicationTime : team.aliens.dms_android.domain.exception.StudyRoomException("Not study room application time")

    object StudyRoomSeatNotFound : team.aliens.dms_android.domain.exception.StudyRoomException("Study room seat not found")

    object StudyRoomAvailableTimeNotFound :
        team.aliens.dms_android.domain.exception.StudyRoomException("Study room available time not found")

    object StudyRoomSomeoneAlreadyApplied : team.aliens.dms_android.domain.exception.StudyRoomException("Study room someone already applied")
}
