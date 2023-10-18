package team.aliens.dms.android.domain._legacy.repository

import team.aliens.dms.android.domain.model.studyroom.ApplySeatInput
import team.aliens.dms.android.domain.model.studyroom.CancelSeatInput
import team.aliens.dms.android.domain.model.studyroom.FetchAvailableStudyRoomTimesOutput
import team.aliens.dms.android.domain.model.studyroom.FetchCurrentAppliedStudyRoomOutput
import team.aliens.dms.android.domain.model.studyroom.FetchSeatTypesInput
import team.aliens.dms.android.domain.model.studyroom.FetchSeatTypesOutput
import team.aliens.dms.android.domain.model.studyroom.FetchStudyRoomApplicationTimeOutput
import team.aliens.dms.android.domain.model.studyroom.FetchStudyRoomDetailsInput
import team.aliens.dms.android.domain.model.studyroom.FetchStudyRoomDetailsOutput
import team.aliens.dms.android.domain.model.studyroom.FetchStudyRoomsInput
import team.aliens.dms.android.domain.model.studyroom.FetchStudyRoomsOutput

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
