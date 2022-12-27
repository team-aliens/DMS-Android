package com.example.data.remote.api

import com.example.data.remote.response.studyroom.ApplySeatTimeResponse
import com.example.data.remote.response.studyroom.StudyRoomListResponse
import com.example.data.remote.response.studyroom.StudyRoomTypeResponse
import com.example.data.remote.url.DmsUrl
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT

interface StudyRoomApi {
    @PUT(DmsUrl.StudyRoom.Apply)
    suspend fun applySeat()

    @GET(DmsUrl.StudyRoom.fetchApplyTime)
    suspend fun fetchApplySeatTime(): ApplySeatTimeResponse

    @DELETE(DmsUrl.StudyRoom.CancelApply)
    suspend fun cancelApplySeat()

    @GET(DmsUrl.StudyRoom.StudyRoomList)
    suspend fun fetchStudyRoomList(): StudyRoomListResponse

    @GET(DmsUrl.StudyRoom.StudyRoomType)
    suspend fun fetchStudyRoomType(): StudyRoomTypeResponse
}