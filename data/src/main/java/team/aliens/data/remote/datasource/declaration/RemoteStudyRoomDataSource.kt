package team.aliens.data.remote.datasource.declaration

import team.aliens.data.remote.response.studyroom.*

interface RemoteStudyRoomDataSource {
    suspend fun applySeat(data: String)

    suspend fun fetchApplySeatTime(): ApplySeatTimeResponse

    suspend fun cancelApplySeat()

    suspend fun fetchStudyRoomList(): StudyRoomListResponse

    suspend fun fetchStudyRoomType(): StudyRoomTypeResponse

    suspend fun fetchStudyRoomDetail(roomId: String): StudyRoomDetailResponse

    suspend fun fetchCurrentStudyRoomOption(): CurrentStudyRoomOptionResponse
}