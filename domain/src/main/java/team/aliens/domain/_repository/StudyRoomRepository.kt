package team.aliens.domain._repository

import team.aliens.domain._model.studyroom.*
import java.util.*

interface StudyRoomRepository {

    suspend fun queryStudyRoomApplicationTime(): FetchStudyRoomApplicationTimeOutput

    suspend fun applySeat(
        seatId: UUID,
        timeSlot: UUID?,
    )

    suspend fun cancelSeat(
        timeSlot: UUID?,
    )

    suspend fun queryStudyRooms(
        timeSlot: UUID,
    ): FetchStudyRoomsOutput

    suspend fun queryStudyRoomDetails(
        studyRoomId: UUID,
        timeSlot: UUID,
    ): FetchStudyRoomDetailsOutput

    suspend fun queryCurrentAppliedStudyRoom(): FetchCurrentAppliedStudyRoomOutput

    suspend fun querySeatTypes(): FetchSeatTypesOutput

    suspend fun queryAvailableStudyRoomTimes(): FetchAvailableStudyRoomTimesOutput
}
