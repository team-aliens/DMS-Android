package team.aliens.data.remote.api

import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import team.aliens.data.remote.response.studyroom.*
import team.aliens.data.remote.url.DmsUrl

interface StudyRoomApi {

    @PUT(DmsUrl.StudyRoom.Apply)
    suspend fun applySeat(
        @Path("seat-id") data: String,
    )

    @GET(DmsUrl.StudyRoom.fetchApplyTime)
    suspend fun fetchApplySeatTime(): ApplySeatTimeResponse

    @DELETE(DmsUrl.StudyRoom.CancelApply)
    suspend fun cancelApplySeat(): Response<Unit>

    @GET(DmsUrl.StudyRoom.StudyRoomList)
    suspend fun fetchStudyRoomList(): StudyRoomListResponse

    @GET(DmsUrl.StudyRoom.StudyRoomType)
    suspend fun fetchStudyRoomType(): StudyRoomTypeResponse

    @GET(DmsUrl.StudyRoom.StudyRoomDetail)
    suspend fun fetchStudyRoomDetail(
        @Path("study-room-id") roomId: String,
    ): StudyRoomDetailResponse

    @GET(DmsUrl.StudyRoom.CurrentStudyRoomOption)
    suspend fun fetchCurrentStudyRoomOption(): CurrentStudyRoomOptionResponse
}