package team.aliens.dms.android.data.studyroom.repository

import team.aliens.dms.android.data.studyroom.mapper.toModel
import team.aliens.dms.android.data.studyroom.model.AppliedStudyRoom
import team.aliens.dms.android.data.studyroom.model.StudyRoom
import team.aliens.dms.android.data.studyroom.model.StudyRoomApplicationTime
import team.aliens.dms.android.data.studyroom.model.Timeslot
import team.aliens.dms.android.network.studyroom.datasource.NetworkStudyRoomDataSource
import java.util.UUID
import javax.inject.Inject

internal class StudyRoomRepositoryImpl @Inject constructor(
    private val networkStudyRoomDataSource: NetworkStudyRoomDataSource,
) : StudyRoomRepository() {

    override suspend fun fetchStudyRoomApplicationTime(): StudyRoomApplicationTime {
        TODO("Not yet implemented")
    }

    override suspend fun applySeat(
        seatId: UUID,
        timeslot: UUID,
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun cancelSeat(
        seatId: UUID,
        timeslot: UUID,
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun fetchStudyRooms(timeslot: UUID): List<StudyRoom> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchStudyRoomDetails(
        studyRoomId: UUID,
        timeslot: UUID,
    ): List<StudyRoom.Details> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchAppliedStudyRoom(): AppliedStudyRoom =
        networkStudyRoomDataSource.fetchAppliedStudyRoom().toModel()

    override suspend fun fetchSeatTypes(studyRoomId: UUID): StudyRoom.Seat.Type {
        TODO("Not yet implemented")
    }

    override suspend fun fetchAvailableStudyRoomTimes(): List<Timeslot> {
        TODO("Not yet implemented")
    }
}
