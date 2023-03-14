package team.aliens.domain.repository

import java.util.UUID
import team.aliens.domain.entity.studyroom.ApplySeatTimeEntity
import team.aliens.domain.entity.studyroom.CurrentStudyRoomOptionEntity
import team.aliens.domain.entity.studyroom.SeatTypeEntity
import team.aliens.domain.entity.studyroom.StudyRoomAvailableTimeListEntity
import team.aliens.domain.entity.studyroom.StudyRoomDetailEntity
import team.aliens.domain.entity.studyroom.StudyRoomListEntity

interface StudyRoomRepository {

    suspend fun applySeat(data: String)

    suspend fun fetchApplySeatTime(): ApplySeatTimeEntity

    suspend fun cancelApplySeat()

    suspend fun fetchStudyRoomList(
        timeSlot: UUID,
    ): StudyRoomListEntity

    suspend fun fetchStudyRoomType(): SeatTypeEntity

    suspend fun fetchStudyRoomDetail(roomId: String): StudyRoomDetailEntity

    suspend fun fetchCurrentStudyRoomOption(): CurrentStudyRoomOptionEntity

    suspend fun fetchStudyRoomAvailableTimeList(): StudyRoomAvailableTimeListEntity
}