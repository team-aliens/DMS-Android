package team.aliens.domain.repository

import team.aliens.domain.entity.studyroom.ApplySeatTimeEntity
import team.aliens.domain.entity.studyroom.StudyRoomDetailEntity
import team.aliens.domain.entity.studyroom.StudyRoomListEntity
import team.aliens.domain.entity.studyroom.StudyRoomTypeEntity

interface StudyRoomRepository {

    suspend fun applySeat(data: String)

    suspend fun fetchApplySeatTime(): ApplySeatTimeEntity

    suspend fun cancelApplySeat()

    suspend fun fetchStudyRoomList(): StudyRoomListEntity

    suspend fun fetchStudyRoomType(): StudyRoomTypeEntity

    suspend fun fetchStudyRoomDetail(roomId: String): StudyRoomDetailEntity
}