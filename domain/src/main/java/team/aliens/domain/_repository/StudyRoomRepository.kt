package team.aliens.domain._repository

import team.aliens.domain._model.studyroom.FetchAvailableStudyRoomTimesOutput
import team.aliens.domain._model.studyroom.FetchCurrentAppliedStudyRoomOutput
import team.aliens.domain._model.studyroom.FetchSeatTypesOutput
import team.aliens.domain._model.studyroom.FetchStudyRoomApplicationTimeOutput
import team.aliens.domain._model.studyroom.FetchStudyRoomDetailsOutput
import team.aliens.domain._model.studyroom.FetchStudyRoomsOutput
import java.util.*

interface StudyRoomRepository {

    suspend fun fetchStudyRoomApplicationTime(): FetchStudyRoomApplicationTimeOutput

    suspend fun applySeat(
        seatId: UUID,
        timeSlot: UUID,
    )

    suspend fun cancelSeat(
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

    suspend fun fetchSeatTypes(): FetchSeatTypesOutput

    suspend fun fetchAvailableStudyRoomTimes(): FetchAvailableStudyRoomTimesOutput
}
