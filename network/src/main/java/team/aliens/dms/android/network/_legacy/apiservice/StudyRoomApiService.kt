package team.aliens.dms.android.network._legacy.apiservice

import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import team.aliens.dms.android.network.annotation.RequiresAccessToken
import team.aliens.dms.android.network.common.HttpPath.StudyRoom.ApplySeat
import team.aliens.dms.android.network.common.HttpPath.StudyRoom.CancelSeat
import team.aliens.dms.android.network.common.HttpPath.StudyRoom.FetchAvailableStudyRoomTimes
import team.aliens.dms.android.network.common.HttpPath.StudyRoom.FetchCurrentAppliedStudyRoom
import team.aliens.dms.android.network.common.HttpPath.StudyRoom.FetchSeatTypes
import team.aliens.dms.android.network.common.HttpPath.StudyRoom.FetchStudyRoomApplicationTime
import team.aliens.dms.android.network.common.HttpPath.StudyRoom.FetchStudyRoomDetails
import team.aliens.dms.android.network.common.HttpPath.StudyRoom.FetchStudyRooms
import team.aliens.dms.android.network.common.HttpProperty.PathVariable.SeatId
import team.aliens.dms.android.network.common.HttpProperty.PathVariable.StudyRoomId
import team.aliens.dms.android.network.common.HttpProperty.QueryString
import team.aliens.dms.android.network.common.HttpProperty.QueryString.TimeSlot
import team.aliens.dms.android.network.model.studyroom.FetchAvailableStudyRoomTimesResponse
import team.aliens.dms.android.network.model.studyroom.FetchCurrentAppliedStudyRoomResponse
import team.aliens.dms.android.network.model.studyroom.FetchSeatTypesResponse
import team.aliens.dms.android.network.model.studyroom.FetchStudyRoomApplicationTimeResponse
import team.aliens.dms.android.network.model.studyroom.FetchStudyRoomDetailsResponse
import team.aliens.dms.android.network.model.studyroom.FetchStudyRoomsResponse
import java.util.*

interface StudyRoomApiService {

    @GET(FetchStudyRoomApplicationTime)
    @RequiresAccessToken
    suspend fun fetchStudyRoomApplicationTime(): FetchStudyRoomApplicationTimeResponse

    @PUT(ApplySeat)
    @RequiresAccessToken
    suspend fun applySeat(
        @Path(SeatId) seatId: UUID,
        @Query(TimeSlot) timeSlot: UUID,
    )

    @DELETE(CancelSeat)
    @RequiresAccessToken
    suspend fun cancelSeat(
        @Path(SeatId) seatId: UUID,
        @Query(TimeSlot) timeSlot: UUID,
    ): Response<Unit>

    @GET(FetchStudyRooms)
    @RequiresAccessToken
    suspend fun fetchStudyRooms(
        @Query(TimeSlot) timeSlot: UUID,
    ): FetchStudyRoomsResponse

    @GET(FetchStudyRoomDetails)
    @RequiresAccessToken
    suspend fun fetchStudyRoomDetails(
        @Path(StudyRoomId) studyRoomId: UUID,
        @Query(TimeSlot) timeSlot: UUID,
    ): FetchStudyRoomDetailsResponse

    @GET(FetchCurrentAppliedStudyRoom)
    @RequiresAccessToken
    suspend fun fetchCurrentAppliedStudyRoom(): FetchCurrentAppliedStudyRoomResponse

    @GET(FetchSeatTypes)
    @RequiresAccessToken
    suspend fun fetchSeatTypes(
        @Query(QueryString.StudyRoomId) studyRoomId: UUID,
    ): FetchSeatTypesResponse

    @GET(FetchAvailableStudyRoomTimes)
    @RequiresAccessToken
    suspend fun fetchAvailableStudyRoomTimes(): FetchAvailableStudyRoomTimesResponse
}
