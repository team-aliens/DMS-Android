package com.example.domain.repository

import com.example.domain.entity.studyroom.ApplySeatTimeEntity
import com.example.domain.entity.studyroom.StudyRoomListEntity
import kotlinx.coroutines.flow.Flow

interface StudyRoomRepository {

    suspend fun applySeat()

    suspend fun fetchApplySeatTime(): Flow<ApplySeatTimeEntity>

    suspend fun cancelApplySeat()

    suspend fun fetchStudyRoomList(): Flow<StudyRoomListEntity>
}