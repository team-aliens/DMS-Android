package team.aliens.remote.apiservice

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import team.aliens.remote.annotation.RequiresAccessToken
import team.aliens.remote.common.HttpPath.School.ExamineSchoolVerificationCode
import team.aliens.remote.common.HttpPath.School.ExamineSchoolVerificationQuestion
import team.aliens.remote.common.HttpPath.School.FetchAvailableFeatures
import team.aliens.remote.common.HttpPath.School.FetchSchoolVerificationQuestion
import team.aliens.remote.common.HttpPath.School.FetchSchools
import team.aliens.remote.common.HttpProperty.PathVariable.SchoolId
import team.aliens.remote.common.HttpProperty.QueryString.Answer
import team.aliens.remote.common.HttpProperty.QueryString.SchoolCode
import team.aliens.remote.model.school.ExamineSchoolVerificationCodeResponse
import team.aliens.remote.model.school.FetchAvailableFeaturesResponse
import team.aliens.remote.model.school.FetchSchoolVerificationQuestionResponse
import team.aliens.remote.model.school.FetchSchoolsResponse
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
        @Path(SchoolId) schoolId: UUID,
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
