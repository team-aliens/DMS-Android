package team.aliens.domain.repository

import java.util.UUID
import team.aliens.domain.entity.studyroom.ApplySeatTimeEntity
import team.aliens.domain.entity.studyroom.CurrentStudyRoomOptionEntity
import team.aliens.domain.entity.studyroom.SeatTypeEntity
import team.aliens.domain.entity.studyroom.StudyRoomAvailableTimeListEntity
import team.aliens.domain.entity.studyroom.StudyRoomDetailEntity
import team.aliens.domain.entity.studyroom.StudyRoomListEntity
import team.aliens.domain.param.ApplyStudyRoomParam
import team.aliens.domain.param.StudyRoomDetailParam

interface StudyRoomRepository {

    suspend fun applySeat(applyStudyRoomParam: ApplyStudyRoomParam)

    suspend fun fetchApplySeatTime(): ApplySeatTimeEntity

    suspend fun cancelApplySeat(
        timeSlot: UUID,
    )

    suspend fun fetchStudyRoomList(
        timeSlot: UUID,
    ): StudyRoomListEntity

    suspend fun fetchStudyRoomType(
        studyRoomId: UUID,
    ): SeatTypeEntity

    suspend fun fetchStudyRoomDetail(
        studyRoomDetailParam: StudyRoomDetailParam,
    ): StudyRoomDetailEntity

    suspend fun fetchCurrentStudyRoomOption(): CurrentStudyRoomOptionEntity

    suspend fun fetchStudyRoomAvailableTimeList(): StudyRoomAvailableTimeListEntity
}