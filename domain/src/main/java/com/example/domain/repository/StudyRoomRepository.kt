package com.example.domain.repository

import com.example.domain.entity.studyroom.ApplySeatTimeEntity
import com.example.domain.entity.studyroom.StudyRoomDetailEntity
import com.example.domain.entity.studyroom.StudyRoomListEntity
import com.example.domain.entity.studyroom.SeatTypeEntity

interface StudyRoomRepository {

    suspend fun applySeat(data: String)

    suspend fun fetchApplySeatTime(): ApplySeatTimeEntity

    suspend fun cancelApplySeat()

    suspend fun fetchStudyRoomList(): StudyRoomListEntity

    suspend fun fetchStudyRoomType(): SeatTypeEntity

    suspend fun fetchStudyRoomDetail(roomId: String): StudyRoomDetailEntity
}