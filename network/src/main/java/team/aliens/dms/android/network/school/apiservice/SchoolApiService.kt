package team.aliens.dms.android.network.school.apiservice

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import team.aliens.dms.android.network.school.model.ExamineSchoolVerificationCodeResponse
import team.aliens.dms.android.network.school.model.FetchSchoolVerificationQuestionResponse
import team.aliens.dms.android.network.school.model.FetchSchoolsResponse
import java.util.UUID

internal interface SchoolApiService {

    @GET("/schools")
    suspend fun fetchSchools(): FetchSchoolsResponse

    @GET("/schools/question/{school-id}")
    suspend fun fetchSchoolVerificationQuestion(@Path("school-id") schoolId: UUID): FetchSchoolVerificationQuestionResponse

    @GET("/schools/answer/{school-id}")
    suspend fun examineSchoolVerificationQuestion(
        @Path("school-id") schoolId: UUID,
        @Query("answer") answer: String,
    )

    @GET("/schools/code")
    suspend fun examineSchoolVerificationCode(@Query("school_code") schoolCode: String): ExamineSchoolVerificationCodeResponse
}
