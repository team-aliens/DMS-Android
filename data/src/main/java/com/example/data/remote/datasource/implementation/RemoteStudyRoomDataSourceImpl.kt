package com.example.data.remote.datasource.implementation

import com.example.data.remote.api.StudyRoomApi
import com.example.data.remote.datasource.declaration.RemoteStudyRoomDataSource
import com.example.data.remote.response.studyroom.ApplySeatTimeResponse
import com.example.data.remote.response.studyroom.StudyRoomDetailResponse
import com.example.data.remote.response.studyroom.StudyRoomListResponse
import com.example.data.remote.response.studyroom.StudyRoomTypeResponse
import com.example.data.util.HttpHandler
import com.example.data.util.sendHttpRequest
import javax.inject.Inject

class RemoteStudyRoomDataSourceImpl @Inject constructor(
    private val studyRoomApi: StudyRoomApi,
) : RemoteStudyRoomDataSource {

    override suspend fun applySeat() =
        sendHttpRequest(httpRequest = suspend { studyRoomApi.applySeat() })

    override suspend fun fetchApplySeatTime() =
        sendHttpRequest(httpRequest = suspend { studyRoomApi.fetchApplySeatTime() })

    override suspend fun cancelApplySeat() =
        sendHttpRequest(httpRequest = suspend { studyRoomApi.cancelApplySeat() })

    override suspend fun fetchStudyRoomList() =
        sendHttpRequest(httpRequest = suspend { studyRoomApi.fetchStudyRoomList() })

    override suspend fun fetchStudyRoomType() =
        sendHttpRequest(httpRequest = suspend { studyRoomApi.fetchStudyRoomType() })

    override suspend fun fetchStudyRoomDetail(roomId: String) =
        sendHttpRequest(httpRequest = suspend { studyRoomApi.fetchStudyRoomDetail(roomId) })
}