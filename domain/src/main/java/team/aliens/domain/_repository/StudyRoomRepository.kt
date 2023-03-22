package team.aliens.domain._repository

import team.aliens.domain._model.studyroom.*
import java.util.*

interface StudyRoomRepository {

    suspend fun queryStudyRoomApplicationTime(): QueryStudyRoomApplicationTimeResult

    suspend fun applySeat(
        seatId: UUID,
        timeSlot: UUID?,
    )

    suspend fun cancelSeat(
        timeSlot: UUID?,
    )

    suspend fun queryStudyRooms(
        timeSlot: UUID,
    ): QueryStudyRoomsResult

    suspend fun queryStudyRoomDetails(
        studyRoomId: UUID,
        timeSlot: UUID,
    ): QueryStudyRoomDetailsResult

    suspend fun queryCurrentAppliedStudyRoom(): QueryCurrentAppliedStudyRoomResult

    suspend fun querySeatTypes(): QuerySeatTypesResult

    suspend fun queryAvailableStudyRoomTimes(): QueryAvailableStudyRoomTimesResult
}
