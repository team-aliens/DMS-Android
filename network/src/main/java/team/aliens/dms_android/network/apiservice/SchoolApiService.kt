package team.aliens.dms_android.network.apiservice

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import team.aliens.dms_android.network.annotation.RequiresAccessToken
import team.aliens.dms_android.network.common.HttpPath.School.ExamineSchoolVerificationCode
import team.aliens.dms_android.network.common.HttpPath.School.ExamineSchoolVerificationQuestion
import team.aliens.dms_android.network.common.HttpPath.School.FetchAvailableFeatures
import team.aliens.dms_android.network.common.HttpPath.School.FetchSchoolVerificationQuestion
import team.aliens.dms_android.network.common.HttpPath.School.FetchSchools
import team.aliens.dms_android.network.common.HttpProperty.PathVariable.SchoolId
import team.aliens.dms_android.network.common.HttpProperty.QueryString.Answer
import team.aliens.dms_android.network.common.HttpProperty.QueryString.SchoolCode
import team.aliens.dms_android.network.model.school.ExamineSchoolVerificationCodeResponse
import team.aliens.dms_android.network.model.school.FetchAvailableFeaturesResponse
import team.aliens.dms_android.network.model.school.FetchSchoolVerificationQuestionResponse
import team.aliens.dms_android.network.model.school.FetchSchoolsResponse
import java.util.*

interface SchoolApiService {

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
