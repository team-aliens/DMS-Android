package team.aliens.dms.android.network.school.apiservice

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import team.aliens.dms.android.network.school.model.ExamineSchoolVerificationCodeResponse
import team.aliens.dms.android.network.school.model.FetchSchoolVerificationQuestionResponse
import team.aliens.dms.android.network.school.model.FetchSchoolsResponse
import java.util.UUID

internal abstract class SchoolApiService {

    @GET("/schools")
    abstract suspend fun fetchSchools(): FetchSchoolsResponse

    @GET("/schools/question/{school-id}")
    abstract suspend fun fetchSchoolVerificationQuestion(@Path("school-id") schoolId: UUID): FetchSchoolVerificationQuestionResponse

    @GET("/schools/answer/{school-id}")
    abstract suspend fun examineSchoolVerificationQuestion(
        @Path("school-id") schoolId: UUID,
        @Query("answer") answer: String,
    )

    @GET("/schools/code")
    abstract suspend fun examineSchoolVerificationCode(@Query("school_code") schoolCode: String): ExamineSchoolVerificationCodeResponse
}
