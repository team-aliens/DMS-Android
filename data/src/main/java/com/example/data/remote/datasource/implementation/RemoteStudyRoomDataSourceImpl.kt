package com.example.data.remote.datasource.implementation

import com.example.data.remote.api.StudyRoomApi
import com.example.data.remote.datasource.declaration.RemoteStudyRoomDataSource
import com.example.data.remote.response.studyroom.ApplySeatTimeResponse
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
}