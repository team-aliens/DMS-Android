package com.example.data.remote.datasource.declaration

import com.example.data.remote.response.studyroom.ApplySeatTimeResponse
import com.example.data.remote.response.studyroom.StudyRoomDetailResponse
import com.example.data.remote.response.studyroom.StudyRoomListResponse
import com.example.data.remote.response.studyroom.StudyRoomTypeResponse

interface RemoteStudyRoomDataSource {
    suspend fun applySeat(data: String)

    suspend fun fetchApplySeatTime(): ApplySeatTimeResponse

    suspend fun cancelApplySeat()

    suspend fun fetchStudyRoomList(): StudyRoomListResponse

    suspend fun fetchStudyRoomType(): StudyRoomTypeResponse

    suspend fun fetchStudyRoomDetail(roomId: String): StudyRoomDetailResponse
}