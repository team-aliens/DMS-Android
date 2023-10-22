package team.aliens.dms.android.network.studyroom.apiservice

import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import team.aliens.dms.android.core.jwt.RequiresAccessToken
import team.aliens.dms.android.network.studyroom.model.FetchAppliedStudyRoomResponse
import team.aliens.dms.android.network.studyroom.model.FetchAvailableStudyRoomTimesResponse
import team.aliens.dms.android.network.studyroom.model.FetchSeatTypesResponse
import team.aliens.dms.android.network.studyroom.model.FetchStudyRoomApplicationTimeResponse
import team.aliens.dms.android.network.studyroom.model.FetchStudyRoomDetailsResponse
import team.aliens.dms.android.network.studyroom.model.FetchStudyRoomsResponse
import java.util.UUID

internal interface StudyRoomApiService {

    @GET("/study-rooms/available-time")
    @RequiresAccessToken
    suspend fun fetchStudyRoomApplicationTime(): FetchStudyRoomApplicationTimeResponse

    @PUT("/study-rooms/{seat-id}")
    @RequiresAccessToken
    suspend fun applySeat(
        @Path("seat-id") seatId: UUID,
        @Query("time_slot") timeslot: UUID,
    )

    @DELETE("/study-rooms/{seat-id}")
    @RequiresAccessToken
    suspend fun cancelSeat(
        @Path("seat-id") seatId: UUID,
        @Query("time_slot") timeslot: UUID,
    )

    @GET("/study-rooms/list/students")
    @RequiresAccessToken
    suspend fun fetchStudyRooms(@Query("time_slot") timeslot: UUID): FetchStudyRoomsResponse

    @GET("/study-rooms/{study-room-id}/students")
    @RequiresAccessToken
    suspend fun fetchStudyRoomDetails(
        @Path("study-room-id") studyRoomId: UUID,
        @Query("time_slot") timeslot: UUID,
    ): FetchStudyRoomDetailsResponse

    @GET("/study-rooms/my")
    @RequiresAccessToken
    suspend fun fetchAppliedStudyRoom(): FetchAppliedStudyRoomResponse

    @GET("/study-rooms/types")
    @RequiresAccessToken
    suspend fun fetchSeatTypes(@Query("study_room_id") studyRoomId: UUID): FetchSeatTypesResponse

    @GET("/study-rooms/time-slots")
    @RequiresAccessToken
    suspend fun fetchAvailableStudyRoomTimes(): FetchAvailableStudyRoomTimesResponse
}
