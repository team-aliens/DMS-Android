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

internal abstract class StudyRoomApiService {

    @GET("/study-rooms/available-time")
    @RequiresAccessToken
    abstract suspend fun fetchStudyRoomApplicationTime(): FetchStudyRoomApplicationTimeResponse

    @PUT("/study-rooms/{seat-id}")
    @RequiresAccessToken
    abstract suspend fun applySeat(
        @Path("seat-id") seatId: UUID,
        @Query("time_slot") timeslot: UUID,
    )

    @DELETE("/study-rooms/{seat-id}")
    @RequiresAccessToken
    abstract suspend fun cancelSeat(
        @Path("seat-id") seatId: UUID,
        @Query("time_slot") timeslot: UUID,
    )

    @GET("/study-rooms/list/students")
    @RequiresAccessToken
    abstract suspend fun fetchStudyRooms(@Query("time_slot") timeslot: UUID): FetchStudyRoomsResponse

    @GET("/study-rooms/{study-room-id}/students")
    @RequiresAccessToken
    abstract suspend fun fetchStudyRoomDetails(
        @Path("study-room-id") studyRoomId: UUID,
        @Query("time_slot") timeslot: UUID,
    ): FetchStudyRoomDetailsResponse

    @GET("/study-rooms/my")
    @RequiresAccessToken
    abstract suspend fun fetchAppliedStudyRoom(): FetchAppliedStudyRoomResponse

    @GET("/study-rooms/types")
    @RequiresAccessToken
    abstract suspend fun fetchSeatTypes(@Query("study_room_id") studyRoomId: UUID): FetchSeatTypesResponse

    @GET("/study-rooms/time-slots")
    @RequiresAccessToken
    abstract suspend fun fetchAvailableStudyRoomTimes(): FetchAvailableStudyRoomTimesResponse
}
