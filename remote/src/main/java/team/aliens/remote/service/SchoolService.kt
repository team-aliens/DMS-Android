package team.aliens.remote.service

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import team.aliens.data.remote.response.students.FetchSchoolsResponse
import team.aliens.remote.annotation.RequiresAccessToken
import team.aliens.remote.common.HttpPath.Schools.ExamineSchoolVerificationCode
import team.aliens.remote.common.HttpPath.Schools.ExamineSchoolVerificationQuestion
import team.aliens.remote.common.HttpPath.Schools.FetchAvailableFeatures
import team.aliens.remote.common.HttpPath.Schools.FetchSchoolVerificationQuestion
import team.aliens.remote.common.HttpPath.Schools.FetchSchools
import team.aliens.remote.common.HttpProperty.PathVariable.SchoolId
import team.aliens.remote.common.HttpProperty.QueryString.Answer
import team.aliens.remote.common.HttpProperty.QueryString.SchoolCode
import team.aliens.remote.model.school.ExamineSchoolVerificationCodeResponse
import team.aliens.remote.model.school.FetchAvailableFeaturesResponse
import team.aliens.remote.model.school.FetchSchoolVerificationQuestionResponse
import java.util.*

interface SchoolService {

    @GET(FetchSchools)
    suspend fun fetchSchools(): FetchSchoolsResponse

    @GET(FetchSchoolVerificationQuestion)
    suspend fun fetchSchoolVerificationQuestion(
        @Path(SchoolId) schoolId: UUID,
    ): FetchSchoolVerificationQuestionResponse

    @GET(ExamineSchoolVerificationQuestion)
    suspend fun examineSchoolVerificationQuestion(
        @Query(Answer) answer: String,
    )

    @GET(ExamineSchoolVerificationCode)
    suspend fun examineSchoolVerificationCode(
        @Query(SchoolCode) schoolCode: String,
    ): ExamineSchoolVerificationCodeResponse

    @GET(FetchAvailableFeatures)
    @RequiresAccessToken
    suspend fun fetchAvailableFeatures(): FetchAvailableFeaturesResponse
}
