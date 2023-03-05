package team.aliens.domain.repository

import team.aliens.domain.entity.studyroom.*

interface StudyRoomRepository {

    suspend fun applySeat(data: String)

    suspend fun fetchApplySeatTime(): ApplySeatTimeEntity

    suspend fun cancelApplySeat()

    suspend fun fetchStudyRoomList(): StudyRoomListEntity

    suspend fun fetchStudyRoomType(): SeatTypeEntity

    suspend fun fetchStudyRoomDetail(roomId: String): StudyRoomDetailEntity

    suspend fun fetchCurrentStudyRoomOption(): CurrentStudyRoomOptionEntity
}