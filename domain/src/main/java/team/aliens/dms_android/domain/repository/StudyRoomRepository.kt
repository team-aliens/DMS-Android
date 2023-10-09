package team.aliens.dms_android.domain.repository

import team.aliens.domain.model.studyroom.ApplySeatInput
import team.aliens.domain.model.studyroom.CancelSeatInput
import team.aliens.domain.model.studyroom.FetchAvailableStudyRoomTimesOutput
import team.aliens.domain.model.studyroom.FetchCurrentAppliedStudyRoomOutput
import team.aliens.domain.model.studyroom.FetchSeatTypesInput
import team.aliens.domain.model.studyroom.FetchSeatTypesOutput
import team.aliens.domain.model.studyroom.FetchStudyRoomApplicationTimeOutput
import team.aliens.domain.model.studyroom.FetchStudyRoomDetailsInput
import team.aliens.domain.model.studyroom.FetchStudyRoomDetailsOutput
import team.aliens.domain.model.studyroom.FetchStudyRoomsInput
import team.aliens.domain.model.studyroom.FetchStudyRoomsOutput

interface StudyRoomRepository {

    suspend fun fetchStudyRoomApplicationTime(): FetchStudyRoomApplicationTimeOutput

    suspend fun applySeat(
        input: ApplySeatInput,
    )

    suspend fun cancelSeat(
        input: CancelSeatInput,
    )

    suspend fun fetchStudyRooms(
        input: FetchStudyRoomsInput,
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
