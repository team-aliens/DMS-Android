package com.example.data.remote.datasource.declaration

import com.example.data.remote.response.studyroom.ApplySeatTimeResponse
import com.example.data.remote.response.studyroom.StudyRoomListResponse

interface RemoteStudyRoomDataSource {
    suspend fun applySeat()

    suspend fun fetchApplySeatTime(): ApplySeatTimeResponse

    suspend fun cancelApplySeat()

    suspend fun fetchStudyRoomList(): StudyRoomListResponse
}