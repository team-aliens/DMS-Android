package team.aliens.data.remote.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import team.aliens.data.remote.response.schools.SchoolConfirmQuestionResponse
import team.aliens.data.remote.response.schools.SchoolIdResponse
import team.aliens.data.remote.url.DmsUrl
import java.util.*

interface SchoolsApi {

    @GET(DmsUrl.Schools.schoolQuestion)
    suspend fun schoolQuestion(
        @Path("school-id") schoolId: UUID,
    ): SchoolConfirmQuestionResponse

    @GET(DmsUrl.Schools.schoolAnswer)
    suspend fun compareSchoolAnswer(
        @Path("school-id") schoolId: UUID,
        @Query("answer") answer: String,
    )

    @GET(DmsUrl.Schools.schoolCode)
    suspend fun examineSchoolCode(
        @Query("school_code") schoolCode: String,
    ): SchoolIdResponse
}
