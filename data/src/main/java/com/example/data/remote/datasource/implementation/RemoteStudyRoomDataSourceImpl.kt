package com.example.data.remote.datasource.implementation

import com.example.data.remote.api.StudyRoomApi
import com.example.data.remote.datasource.declaration.RemoteStudyRoomDataSource
import com.example.data.remote.response.studyroom.ApplySeatTimeResponse
import com.example.data.remote.response.studyroom.StudyRoomDetailResponse
import com.example.data.remote.response.studyroom.StudyRoomListResponse
import com.example.data.remote.response.studyroom.StudyRoomTypeResponse
import com.example.data.util.HttpHandler
import javax.inject.Inject

class RemoteStudyRoomDataSourceImpl @Inject constructor(
    private val studyRoomApi: StudyRoomApi,
) : RemoteStudyRoomDataSource {

    override suspend fun applySeat() =
        HttpHandler<Unit>()
            .httpRequest { studyRoomApi.applySeat() }
            .sendRequest()

    override suspend fun fetchApplySeatTime() =
        HttpHandler<ApplySeatTimeResponse>()
            .httpRequest { studyRoomApi.fetchApplySeatTime() }
            .sendRequest()

    override suspend fun cancelApplySeat() =
        HttpHandler<Unit>()
            .httpRequest { studyRoomApi.cancelApplySeat() }
            .sendRequest()

    override suspend fun fetchStudyRoomList(): StudyRoomListResponse =
        HttpHandler<StudyRoomListResponse>()
            .httpRequest { studyRoomApi.fetchStudyRoomList() }
            .sendRequest()

    override suspend fun fetchStudyRoomType(): StudyRoomTypeResponse =
        HttpHandler<StudyRoomTypeResponse>()
            .httpRequest { studyRoomApi.fetchStudyRoomType() }
            .sendRequest()

    override suspend fun fetchStudyRoomDetail(): StudyRoomDetailResponse =
        HttpHandler<StudyRoomDetailResponse>()
            .httpRequest { studyRoomApi.fetchStudyRoomDetail() }
            .sendRequest()

}