package team.aliens.remote.service

import retrofit2.http.*
import team.aliens.remote.annotation.RequiresAccessToken
import team.aliens.remote.common.HttpPath.Student.CheckEmailDuplication
import team.aliens.remote.common.HttpPath.Student.CheckIdDuplication
import team.aliens.remote.common.HttpPath.Student.EditProfile
import team.aliens.remote.common.HttpPath.Student.ExamineStudentNumber
import team.aliens.remote.common.HttpPath.Student.FetchMyPage
import team.aliens.remote.common.HttpPath.Student.FindId
import team.aliens.remote.common.HttpPath.Student.ResetPassword
import team.aliens.remote.common.HttpPath.Student.SignUp
import team.aliens.remote.common.HttpPath.Student.Withdraw
import team.aliens.remote.common.HttpProperty.PathVariable
import team.aliens.remote.common.HttpProperty.QueryString.AccountId
import team.aliens.remote.common.HttpProperty.QueryString.ClassRoom
import team.aliens.remote.common.HttpProperty.QueryString.Email
import team.aliens.remote.common.HttpProperty.QueryString.Grade
import team.aliens.remote.common.HttpProperty.QueryString.Name
import team.aliens.remote.common.HttpProperty.QueryString.Number
import team.aliens.remote.common.HttpProperty.QueryString.SchoolId
import team.aliens.remote.model._common.AuthenticationResponse
import team.aliens.remote.model.student.*
import java.util.*

interface StudentService {

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
    )

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
    )

    @DELETE(Withdraw)
    @RequiresAccessToken
    suspend fun withdraw()
}
