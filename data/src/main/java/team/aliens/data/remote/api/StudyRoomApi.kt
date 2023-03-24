package team.aliens.data.remote.api

import java.util.UUID
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import team.aliens.data.remote.response.studyroom.ApplySeatTimeResponse
import team.aliens.data.remote.response.studyroom.StudyRoomTypeResponse
import team.aliens.data.remote.response.studyroom.StudyRoomListResponse
import team.aliens.data.remote.response.studyroom.StudyRoomDetailResponse
import team.aliens.data.remote.response.studyroom.CurrentStudyRoomOptionResponse
import team.aliens.data.remote.response.studyroom.StudyRoomAvailableTimeListResponse
import team.aliens.data.remote.url.DmsUrl

interface StudyRoomApi {

    @PUT(DmsUrl.StudyRoom.Apply)
    suspend fun applySeat(
        @Path("seat-id") seatId: String,
        @Query("time_slot") timeSlot: UUID?,
    )

    @GET(DmsUrl.StudyRoom.fetchApplyTime)
    suspend fun fetchApplySeatTime(): ApplySeatTimeResponse

    @DELETE(DmsUrl.StudyRoom.CancelApply)
    suspend fun cancelApplySeat(): Response<Unit>

    @GET(DmsUrl.StudyRoom.StudyRoomList)
    suspend fun fetchStudyRoomList(
        @Query("time_slot") timeSlot: UUID,
    ): StudyRoomListResponse

    @GET(DmsUrl.StudyRoom.StudyRoomType)
    suspend fun fetchStudyRoomType(): StudyRoomTypeResponse

    @GET(DmsUrl.StudyRoom.StudyRoomDetail)
    suspend fun fetchStudyRoomDetail(
        @Path("study-room-id") roomId: String,
        @Query("time_slot") timeSlot: UUID?,
    ): StudyRoomDetailResponse

    @GET(DmsUrl.StudyRoom.CurrentStudyRoomOption)
    suspend fun fetchCurrentStudyRoomOption(): CurrentStudyRoomOptionResponse

    @GET(DmsUrl.StudyRoom.StudyRoomAvailableTimeList)
    suspend fun fetchStudyRoomAvailableTimeList(): StudyRoomAvailableTimeListResponse
}