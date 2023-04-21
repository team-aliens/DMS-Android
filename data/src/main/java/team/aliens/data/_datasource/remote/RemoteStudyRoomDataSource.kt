package team.aliens.data._datasource.remote

import team.aliens.domain._model.studyroom.ApplySeatInput
import team.aliens.domain._model.studyroom.FetchAvailableStudyRoomTimesOutput
import team.aliens.domain._model.studyroom.FetchCurrentAppliedStudyRoomOutput
import team.aliens.domain._model.studyroom.FetchSeatTypesOutput
import team.aliens.domain._model.studyroom.FetchStudyRoomApplicationTimeOutput
import team.aliens.domain._model.studyroom.FetchStudyRoomDetailsOutput
import team.aliens.domain._model.studyroom.FetchStudyRoomsOutput
import java.util.UUID

interface RemoteStudyRoomDataSource {

    suspend fun fetchStudyRoomApplicationTime(): FetchStudyRoomApplicationTimeOutput

    suspend fun applySeat(
        input: ApplySeatInput,
    )

    suspend fun cancelSeat(
        seatId: UUID,
        timeSlot: UUID,
    )

    suspend fun fetchStudyRooms(
        timeSlot: UUID,
    ): FetchStudyRoomsOutput

    suspend fun fetchStudyRoomDetails(
        studyRoomId: UUID,
        timeSlot: UUID,
    ): FetchStudyRoomDetailsOutput

    suspend fun fetchCurrentAppliedStudyRoom(): FetchCurrentAppliedStudyRoomOutput

    suspend fun fetchSeatTypes(
        studyRoomId: UUID,
    ): FetchSeatTypesOutput

    suspend fun fetchAvailableStudyRoomTimes(): FetchAvailableStudyRoomTimesOutput
}
