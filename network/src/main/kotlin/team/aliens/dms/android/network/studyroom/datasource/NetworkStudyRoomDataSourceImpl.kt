package team.aliens.dms.android.network.studyroom.datasource
import team.aliens.dms.android.network.studyroom.apiservice.StudyRoomApiService
import team.aliens.dms.android.network.studyroom.model.FetchAppliedStudyRoomResponse
import team.aliens.dms.android.network.studyroom.model.FetchAvailableStudyRoomTimesResponse
import team.aliens.dms.android.network.studyroom.model.FetchSeatTypesResponse
import team.aliens.dms.android.network.studyroom.model.FetchStudyRoomApplicationTimeResponse
import team.aliens.dms.android.network.studyroom.model.FetchStudyRoomDetailsResponse
import team.aliens.dms.android.network.studyroom.model.FetchStudyRoomsResponse
import java.util.UUID
import javax.inject.Inject

internal class NetworkStudyRoomDataSourceImpl @Inject constructor(
    private val studyRoomApiService: StudyRoomApiService,
) : NetworkStudyRoomDataSource() {

    override suspend fun fetchStudyRoomApplicationTime(): FetchStudyRoomApplicationTimeResponse =
        studyRoomApiService.fetchStudyRoomApplicationTime()

    override suspend fun applySeat(
        seatId: UUID,
        timeslot: UUID,
    ) {
        studyRoomApiService.applySeat(seatId, timeslot)
    }

    override suspend fun cancelSeat(
        seatId: UUID,
        timeslot: UUID,
    ) {
        studyRoomApiService.cancelSeat(seatId, timeslot)
    }

    override suspend fun fetchStudyRooms(timeslot: UUID): FetchStudyRoomsResponse =
        studyRoomApiService.fetchStudyRooms(timeslot)

    override suspend fun fetchStudyRoomDetails(
        studyRoomId: UUID,
        timeslot: UUID,
    ): FetchStudyRoomDetailsResponse =
        studyRoomApiService.fetchStudyRoomDetails(studyRoomId, timeslot)

    override suspend fun fetchAppliedStudyRoom(): FetchAppliedStudyRoomResponse =
        studyRoomApiService.fetchAppliedStudyRoom()

    override suspend fun fetchSeatTypes(studyRoomId: UUID): FetchSeatTypesResponse =
        studyRoomApiService.fetchSeatTypes(studyRoomId)

    override suspend fun fetchAvailableStudyRoomTimes(): FetchAvailableStudyRoomTimesResponse =
        studyRoomApiService.fetchAvailableStudyRoomTimes()
}
