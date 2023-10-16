package team.aliens.dms.android.network._legacy.apiservice

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import team.aliens.dms.android.network.annotation.RequiresAccessToken
import team.aliens.dms.android.network.common.HttpPath.Student.CheckEmailDuplication
import team.aliens.dms.android.network.common.HttpPath.Student.CheckIdDuplication
import team.aliens.dms.android.network.common.HttpPath.Student.EditProfile
import team.aliens.dms.android.network.common.HttpPath.Student.ExamineStudentNumber
import team.aliens.dms.android.network.common.HttpPath.Student.FetchMyPage
import team.aliens.dms.android.network.common.HttpPath.Student.FindId
import team.aliens.dms.android.network.common.HttpPath.Student.ResetPassword
import team.aliens.dms.android.network.common.HttpPath.Student.SignUp
import team.aliens.dms.android.network.common.HttpPath.Student.Withdraw
import team.aliens.dms.android.network.common.HttpProperty.PathVariable
import team.aliens.dms.android.network.common.HttpProperty.QueryString.AccountId
import team.aliens.dms.android.network.common.HttpProperty.QueryString.ClassRoom
import team.aliens.dms.android.network.common.HttpProperty.QueryString.Email
import team.aliens.dms.android.network.common.HttpProperty.QueryString.Grade
import team.aliens.dms.android.network.common.HttpProperty.QueryString.Name
import team.aliens.dms.android.network.common.HttpProperty.QueryString.Number
import team.aliens.dms.android.network.common.HttpProperty.QueryString.SchoolId
import team.aliens.dms.android.network.model._common.AuthenticationResponse
import team.aliens.dms.android.network.model.student.EditProfileRequest
import team.aliens.dms.android.network.model.student.ExamineStudentNumberResponse
import team.aliens.dms.android.network.model.student.FetchMyPageResponse
import team.aliens.dms.android.network.model.student.FindIdResponse
import team.aliens.dms.android.network.model.student.ResetPasswordRequest
import team.aliens.dms.android.network.model.student.SignUpRequest
import java.util.*

interface StudentApiService {

    @POST(SignUp)
    suspend fun signUp(
        @Body request: SignUpRequest,
    ): AuthenticationResponse

    @GET(ExamineStudentNumber)
    suspend fun examineStudentNumber(
        @Query(SchoolId) schoolId: UUID,
        @Query(Grade) grade: Int,
        @Query(ClassRoom) classRoom: Int,
        @Query(Number) number: Int,
    ): ExamineStudentNumberResponse

    @GET(FindId)
    suspend fun findId(
        @Path(PathVariable.SchoolId) schoolId: UUID,
        @Query(Name) studentName: String,
        @Query(Grade) grade: Int,
        @Query(ClassRoom) classRoom: Int,
        @Query(Number) number: Int,
    ): FindIdResponse

    @PATCH(ResetPassword)
    suspend fun resetPassword(
        @Body request: ResetPasswordRequest,
    ): Response<Unit>

    @GET(CheckIdDuplication)
    suspend fun checkIdDuplication(
        @Query(AccountId) accountId: String,
    )

    @GET(CheckEmailDuplication)
    suspend fun checkEmailDuplication(
        @Query(Email) email: String,
    )

    @GET(FetchMyPage)
    @RequiresAccessToken
    suspend fun fetchMyPage(): FetchMyPageResponse

    @PATCH(EditProfile)
    @RequiresAccessToken
    suspend fun editProfile(
        @Body request: EditProfileRequest,
    ): Response<Unit>

    @DELETE(Withdraw)
    @RequiresAccessToken
    suspend fun withdraw()
}
