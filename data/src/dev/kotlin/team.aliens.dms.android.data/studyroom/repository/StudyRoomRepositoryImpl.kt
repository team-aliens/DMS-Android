package team.aliens.dms.android.data.studyroom.repository

import team.aliens.dms.android.data.studyroom.mapper.toModel
import team.aliens.dms.android.data.studyroom.model.AppliedStudyRoom
import team.aliens.dms.android.data.studyroom.model.AvailableStudyRoomTime
import team.aliens.dms.android.data.studyroom.model.StudyRoom
import team.aliens.dms.android.data.studyroom.model.StudyRoomApplicationTime
import team.aliens.dms.android.network.studyroom.datasource.NetworkStudyRoomDataSource
import java.util.UUID
import javax.inject.Inject

internal class StudyRoomRepositoryImpl @Inject constructor(
    private val networkStudyRoomDataSource: NetworkStudyRoomDataSource,
) : StudyRoomRepository() {

    override suspend fun fetchStudyRoomApplicationTime(): StudyRoomApplicationTime =
        networkStudyRoomDataSource.fetchStudyRoomApplicationTime().toModel()

    override suspend fun applySeat(
        seatId: UUID,
        timeslot: UUID,
    ) {
        networkStudyRoomDataSource.applySeat(
            seatId = seatId,
            timeslot = timeslot,
        )
    }

    override suspend fun cancelSeat(
        seatId: UUID,
        timeslot: UUID,
    ) {
        networkStudyRoomDataSource.cancelSeat(
            seatId = seatId,
            timeslot = timeslot,
        )
    }

    override suspend fun fetchStudyRooms(timeslot: UUID): List<StudyRoom> =
        networkStudyRoomDataSource.fetchStudyRooms(timeslot).toModel()

    override suspend fun fetchStudyRoomDetails(
        studyRoomId: UUID,
        timeslot: UUID,
    ): StudyRoom.Details = networkStudyRoomDataSource.fetchStudyRoomDetails(
        studyRoomId = studyRoomId,
        timeslot = timeslot,
    ).toModel()

    override suspend fun fetchAppliedStudyRoom(): AppliedStudyRoom =
        networkStudyRoomDataSource.fetchAppliedStudyRoom().toModel()

    override suspend fun fetchSeatTypes(studyRoomId: UUID): List<StudyRoom.Seat.Type> =
        networkStudyRoomDataSource.fetchSeatTypes(studyRoomId = studyRoomId).toModel()

    override suspend fun fetchAvailableStudyRoomTimes(): List<AvailableStudyRoomTime> =
        networkStudyRoomDataSource.fetchAvailableStudyRoomTimes().toModel()
}
