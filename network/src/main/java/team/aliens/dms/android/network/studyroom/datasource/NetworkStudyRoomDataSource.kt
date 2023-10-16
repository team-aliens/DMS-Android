package team.aliens.dms.android.network.studyroom.datasource

import team.aliens.dms.android.network.studyroom.model.FetchAppliedStudyRoomResponse
import team.aliens.dms.android.network.studyroom.model.FetchAvailableStudyRoomTimesResponse
import team.aliens.dms.android.network.studyroom.model.FetchSeatTypesResponse
import team.aliens.dms.android.network.studyroom.model.FetchStudyRoomApplicationTimeResponse
import team.aliens.dms.android.network.studyroom.model.FetchStudyRoomDetailsResponse
import team.aliens.dms.android.network.studyroom.model.FetchStudyRoomsResponse
import java.util.UUID

abstract class NetworkStudyRoomDataSource {

    abstract suspend fun fetchStudyRoomApplicationTime(): FetchStudyRoomApplicationTimeResponse

    abstract suspend fun applySeat(
        seatId: UUID,
        timeslot: UUID,
    )

    abstract suspend fun cancelSeat(
        seatId: UUID,
        timeslot: UUID,
    )

    abstract suspend fun fetchStudyRooms(timeslot: UUID): FetchStudyRoomsResponse

    abstract suspend fun fetchStudyRoomDetails(
        studyRoomId: UUID,
        timeslot: UUID,
    ): FetchStudyRoomDetailsResponse

    abstract suspend fun fetchAppliedStudyRoom(): FetchAppliedStudyRoomResponse

    abstract suspend fun fetchSeatTypes(studyRoomId: UUID): FetchSeatTypesResponse

    abstract suspend fun fetchAvailableStudyRoomTimes(): FetchAvailableStudyRoomTimesResponse
}
