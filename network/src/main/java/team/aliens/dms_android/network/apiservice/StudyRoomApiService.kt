package team.aliens.dms_android.network.apiservice

import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import team.aliens.dms_android.network.annotation.RequiresAccessToken
import team.aliens.network.common.HttpPath.StudyRoom.ApplySeat
import team.aliens.network.common.HttpPath.StudyRoom.CancelSeat
import team.aliens.network.common.HttpPath.StudyRoom.FetchAvailableStudyRoomTimes
import team.aliens.network.common.HttpPath.StudyRoom.FetchCurrentAppliedStudyRoom
import team.aliens.network.common.HttpPath.StudyRoom.FetchSeatTypes
import team.aliens.network.common.HttpPath.StudyRoom.FetchStudyRoomApplicationTime
import team.aliens.network.common.HttpPath.StudyRoom.FetchStudyRoomDetails
import team.aliens.network.common.HttpPath.StudyRoom.FetchStudyRooms
import team.aliens.network.common.HttpProperty.PathVariable.SeatId
import team.aliens.network.common.HttpProperty.PathVariable.StudyRoomId
import team.aliens.network.common.HttpProperty.QueryString
import team.aliens.network.common.HttpProperty.QueryString.TimeSlot
import team.aliens.network.model.studyroom.FetchAvailableStudyRoomTimesResponse
import team.aliens.network.model.studyroom.FetchCurrentAppliedStudyRoomResponse
import team.aliens.network.model.studyroom.FetchSeatTypesResponse
import team.aliens.network.model.studyroom.FetchStudyRoomApplicationTimeResponse
import team.aliens.network.model.studyroom.FetchStudyRoomDetailsResponse
import team.aliens.network.model.studyroom.FetchStudyRoomsResponse
import java.util.*

interface StudyRoomApiService {

    @GET(FetchStudyRoomApplicationTime)
    @team.aliens.dms_android.network.annotation.RequiresAccessToken
    suspend fun fetchStudyRoomApplicationTime(): FetchStudyRoomApplicationTimeResponse

    @PUT(ApplySeat)
    @team.aliens.dms_android.network.annotation.RequiresAccessToken
    suspend fun applySeat(
        @Path(SeatId) seatId: UUID,
        @Query(TimeSlot) timeSlot: UUID,
    )

    @DELETE(CancelSeat)
    @team.aliens.dms_android.network.annotation.RequiresAccessToken
    suspend fun cancelSeat(
        @Path(SeatId) seatId: UUID,
        @Query(TimeSlot) timeSlot: UUID,
    ): Response<Unit>

    @GET(FetchStudyRooms)
    @team.aliens.dms_android.network.annotation.RequiresAccessToken
    suspend fun fetchStudyRooms(
        @Query(TimeSlot) timeSlot: UUID,
    ): FetchStudyRoomsResponse

    @GET(FetchStudyRoomDetails)
    @team.aliens.dms_android.network.annotation.RequiresAccessToken
    suspend fun fetchStudyRoomDetails(
        @Path(StudyRoomId) studyRoomId: UUID,
        @Query(TimeSlot) timeSlot: UUID,
    ): FetchStudyRoomDetailsResponse

    @GET(FetchCurrentAppliedStudyRoom)
    @team.aliens.dms_android.network.annotation.RequiresAccessToken
    suspend fun fetchCurrentAppliedStudyRoom(): FetchCurrentAppliedStudyRoomResponse

    @GET(FetchSeatTypes)
    @team.aliens.dms_android.network.annotation.RequiresAccessToken
    suspend fun fetchSeatTypes(
        @Query(QueryString.StudyRoomId) studyRoomId: UUID,
    ): FetchSeatTypesResponse

    @GET(FetchAvailableStudyRoomTimes)
    @team.aliens.dms_android.network.annotation.RequiresAccessToken
    suspend fun fetchAvailableStudyRoomTimes(): FetchAvailableStudyRoomTimesResponse
}
