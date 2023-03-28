package team.aliens.remote.service

import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import team.aliens.remote.annotation.RequiresAccessToken
import team.aliens.remote.common.DormHttpPath.StudyRooms.ApplySeat
import team.aliens.remote.common.DormHttpPath.StudyRooms.CancelSeat
import team.aliens.remote.common.DormHttpPath.StudyRooms.FetchAvailableStudyRoomTimes
import team.aliens.remote.common.DormHttpPath.StudyRooms.FetchCurrentAppliedStudyRoom
import team.aliens.remote.common.DormHttpPath.StudyRooms.FetchSeatTypes
import team.aliens.remote.common.DormHttpPath.StudyRooms.FetchStudyRoomApplicationTime
import team.aliens.remote.common.DormHttpPath.StudyRooms.FetchStudyRoomDetails
import team.aliens.remote.common.DormHttpPath.StudyRooms.FetchStudyRooms
import team.aliens.remote.common.HttpProperty.PathVariable.SeatId
import team.aliens.remote.common.HttpProperty.PathVariable.StudyRoomId
import team.aliens.remote.common.HttpProperty.QueryString.TimeSlot
import team.aliens.remote.model.studyroom.FetchAvailableStudyRoomTimesResponse
import team.aliens.remote.model.studyroom.FetchCurrentAppliedStudyRoomResponse
import team.aliens.remote.model.studyroom.FetchSeatTypesResponse
import team.aliens.remote.model.studyroom.FetchStudyRoomApplicationTimeResponse
import team.aliens.remote.model.studyroom.FetchStudyRoomDetailsResponse
import team.aliens.remote.model.studyroom.FetchStudyRoomsResponse
import java.util.*

interface StudyRoomService {

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
        @Query(TimeSlot) timeSlot: UUID,
    )

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
    suspend fun fetchSeatTypes(): FetchSeatTypesResponse

    @GET(FetchAvailableStudyRoomTimes)
    @RequiresAccessToken
    suspend fun fetchAvailableStudyRoomTimes(): FetchAvailableStudyRoomTimesResponse
}
