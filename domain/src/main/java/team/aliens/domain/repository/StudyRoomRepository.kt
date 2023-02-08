package team.aliens.domain.repository

import com.example.domain.entity.studyroom.ApplySeatTimeEntity
import com.example.domain.entity.studyroom.StudyRoomDetailEntity
import com.example.domain.entity.studyroom.StudyRoomListEntity
import com.example.domain.entity.studyroom.StudyRoomTypeEntity

interface StudyRoomRepository {

    suspend fun applySeat(data: String)

    suspend fun fetchApplySeatTime(): ApplySeatTimeEntity

    suspend fun cancelApplySeat()

    suspend fun fetchStudyRoomList(): StudyRoomListEntity

    suspend fun fetchStudyRoomType(): StudyRoomTypeEntity

    suspend fun fetchStudyRoomDetail(roomId: String): StudyRoomDetailEntity
}