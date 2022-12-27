package com.example.domain.repository

import com.example.domain.entity.studyroom.ApplySeatTimeEntity
import com.example.domain.entity.studyroom.StudyRoomDetailEntity
import com.example.domain.entity.studyroom.StudyRoomListEntity
import com.example.domain.entity.studyroom.StudyRoomTypeEntity
import kotlinx.coroutines.flow.Flow

interface StudyRoomRepository {

    suspend fun applySeat()

    suspend fun fetchApplySeatTime(): ApplySeatTimeEntity

    suspend fun cancelApplySeat()

    suspend fun fetchStudyRoomList(): StudyRoomListEntity

    suspend fun fetchStudyRoomType(): StudyRoomTypeEntity

    suspend fun fetchStudyRoomDetail(roomId: String): StudyRoomDetailEntity
}