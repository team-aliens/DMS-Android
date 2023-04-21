package team.aliens.domain._repository

import team.aliens.domain._model.studyroom.ApplySeatInput
import team.aliens.domain._model.studyroom.CancelSeatInput
import team.aliens.domain._model.studyroom.FetchAvailableStudyRoomTimesOutput
import team.aliens.domain._model.studyroom.FetchCurrentAppliedStudyRoomOutput
import team.aliens.domain._model.studyroom.FetchSeatTypesInput
import team.aliens.domain._model.studyroom.FetchSeatTypesOutput
import team.aliens.domain._model.studyroom.FetchStudyRoomApplicationTimeOutput
import team.aliens.domain._model.studyroom.FetchStudyRoomDetailsInput
import team.aliens.domain._model.studyroom.FetchStudyRoomDetailsOutput
import team.aliens.domain._model.studyroom.FetchStudyRoomsOutput
import java.util.UUID

interface StudyRoomRepository {

    suspend fun fetchStudyRoomApplicationTime(): FetchStudyRoomApplicationTimeOutput

    suspend fun applySeat(
        input: ApplySeatInput,
    )

    suspend fun cancelSeat(
        input: CancelSeatInput,
    )

    suspend fun fetchStudyRooms(
        timeSlot: UUID,
    ): FetchStudyRoomsOutput

    suspend fun fetchStudyRoomDetails(
        input: FetchStudyRoomDetailsInput,
    ): FetchStudyRoomDetailsOutput

    suspend fun fetchCurrentAppliedStudyRoom(): FetchCurrentAppliedStudyRoomOutput

    suspend fun fetchSeatTypes(
        input: FetchSeatTypesInput,
    ): FetchSeatTypesOutput

    suspend fun fetchAvailableStudyRoomTimes(): FetchAvailableStudyRoomTimesOutput
}
