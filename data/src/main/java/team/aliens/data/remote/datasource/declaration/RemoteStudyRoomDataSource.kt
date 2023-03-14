package team.aliens.data.remote.datasource.declaration

import java.util.UUID
import team.aliens.data.remote.response.studyroom.ApplySeatTimeResponse
import team.aliens.data.remote.response.studyroom.CurrentStudyRoomOptionResponse
import team.aliens.data.remote.response.studyroom.StudyRoomAvailableTimeListResponse
import team.aliens.data.remote.response.studyroom.StudyRoomDetailResponse
import team.aliens.data.remote.response.studyroom.StudyRoomListResponse
import team.aliens.data.remote.response.studyroom.StudyRoomTypeResponse

interface RemoteStudyRoomDataSource {
    suspend fun applySeat(data: String)

    suspend fun fetchApplySeatTime(): ApplySeatTimeResponse

    suspend fun cancelApplySeat()

    suspend fun fetchStudyRoomList(
        timeSlot: UUID,
    ): StudyRoomListResponse

    suspend fun fetchStudyRoomType(): StudyRoomTypeResponse

    suspend fun fetchStudyRoomDetail(roomId: String): StudyRoomDetailResponse

    suspend fun fetchCurrentStudyRoomOption(): CurrentStudyRoomOptionResponse

    suspend fun fetchStudyRoomAvailableTimeList(): StudyRoomAvailableTimeListResponse
}