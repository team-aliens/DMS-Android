package team.aliens.dms.android.network.studyroom.datasource

import team.aliens.dms.android.core.network.util.handleNetworkRequest
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
        handleNetworkRequest { studyRoomApiService.fetchStudyRoomApplicationTime() }

    override suspend fun applySeat(
        seatId: UUID,
        timeslot: UUID,
    ) {
        handleNetworkRequest { studyRoomApiService.applySeat(seatId, timeslot) }
    }

    override suspend fun cancelSeat(
        seatId: UUID,
        timeslot: UUID,
    ) {
        handleNetworkRequest { studyRoomApiService.cancelSeat(seatId, timeslot) }
    }

    override suspend fun fetchStudyRooms(timeslot: UUID): FetchStudyRoomsResponse =
        handleNetworkRequest { studyRoomApiService.fetchStudyRooms(timeslot) }

    override suspend fun fetchStudyRoomDetails(
        studyRoomId: UUID,
        timeslot: UUID,
    ): FetchStudyRoomDetailsResponse =
        handleNetworkRequest { studyRoomApiService.fetchStudyRoomDetails(studyRoomId, timeslot) }

    override suspend fun fetchAppliedStudyRoom(): FetchAppliedStudyRoomResponse =
        handleNetworkRequest { studyRoomApiService.fetchAppliedStudyRoom() }

    override suspend fun fetchSeatTypes(studyRoomId: UUID): FetchSeatTypesResponse =
        handleNetworkRequest { studyRoomApiService.fetchSeatTypes(studyRoomId) }

    override suspend fun fetchAvailableStudyRoomTimes(): FetchAvailableStudyRoomTimesResponse =
        handleNetworkRequest { studyRoomApiService.fetchAvailableStudyRoomTimes() }
}
