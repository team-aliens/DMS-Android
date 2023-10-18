package team.aliens.dms.android.domain._legacy.exception

sealed class StudyRoomException(
    message: String,
) : RuntimeException(message) {

    object StudyRoomGenderGradeMismatch : team.aliens.dms.android.domain._legacy.exception.StudyRoomException("Study room gender and grade mismatch")

    object NotStudyRoomApplicationTime : team.aliens.dms.android.domain._legacy.exception.StudyRoomException("Not study room application time")

    object StudyRoomSeatNotFound : team.aliens.dms.android.domain._legacy.exception.StudyRoomException("Study room seat not found")

    object StudyRoomAvailableTimeNotFound :
        team.aliens.dms.android.domain._legacy.exception.StudyRoomException("Study room available time not found")

    object StudyRoomSomeoneAlreadyApplied : team.aliens.dms.android.domain._legacy.exception.StudyRoomException("Study room someone already applied")
}
