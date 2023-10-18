package team.aliens.dms.android.data.studyroom.repository

import team.aliens.dms.android.data.studyroom.model.AppliedStudyRoom
import team.aliens.dms.android.data.studyroom.model.StudyRoom
import team.aliens.dms.android.data.studyroom.model.StudyRoomApplicationTime
import team.aliens.dms.android.data.studyroom.model.Timeslot
import java.util.UUID

abstract class StudyRoomRepository {

    abstract suspend fun fetchStudyRoomApplicationTime(): StudyRoomApplicationTime

    abstract suspend fun applySeat(
        seatId: UUID,
        timeslot: UUID,
    )

    abstract suspend fun cancelSeat(
        seatId: UUID,
        timeslot: UUID,
    )

    abstract suspend fun fetchStudyRooms(timeslot: UUID): List<StudyRoom>

    abstract suspend fun fetchStudyRoomDetails(
        studyRoomId: UUID,
        timeslot: UUID,
    ): List<StudyRoom.Details>

    abstract suspend fun fetchAppliedStudyRoom(): AppliedStudyRoom

    abstract suspend fun fetchSeatTypes(studyRoomId: UUID): StudyRoom.Seat.Type

    abstract suspend fun fetchAvailableStudyRoomTimes(): List<Timeslot>
}
