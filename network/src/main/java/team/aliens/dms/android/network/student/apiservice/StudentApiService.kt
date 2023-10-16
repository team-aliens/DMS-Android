package team.aliens.dms.android.network.student.apiservice

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import team.aliens.dms.android.core.jwt.RequiresAccessToken
import team.aliens.dms.android.core.jwt.network.model.AuthenticationResponse
import team.aliens.dms.android.network.student.model.EditProfileRequest
import team.aliens.dms.android.network.student.model.ExamineStudentNumberResponse
import team.aliens.dms.android.network.student.model.FetchMyPageResponse
import team.aliens.dms.android.network.student.model.FindIdResponse
import team.aliens.dms.android.network.student.model.ResetPasswordRequest
import team.aliens.dms.android.network.student.model.SignUpRequest
import java.util.UUID

internal abstract class StudentApiService {

    @POST("/students/signup")
    abstract suspend fun signUp(@Body request: SignUpRequest): AuthenticationResponse

    @GET("/students/name")
    abstract suspend fun examineStudentNumber(
        @Query("school_id") schoolId: UUID,
        @Query("grade") grade: Int,
        @Query("class_room") classRoom: Int,
        @Query("number") number: Int,
    ): ExamineStudentNumberResponse

    @GET("/students/account-id/{school-id}")
    abstract suspend fun findId(
        @Path("school-id") schoolId: UUID,
        @Query("name") studentName: String,
        @Query("grade") grade: Int,
        @Query("class_room") classRoom: Int,
        @Query("number") number: Int,
    ): FindIdResponse

    @PATCH("/students/password/initialization")
    abstract suspend fun resetPassword(@Body request: ResetPasswordRequest)

    @GET("/students/account-id/duplication")
    abstract suspend fun checkIdDuplication(@Query("account_id") accountId: String)

    @GET("/students/email/duplication")
    abstract suspend fun checkEmailDuplication(@Query("email") email: String)

    @GET("/students/profile")
    @RequiresAccessToken
    abstract suspend fun fetchMyPage(): FetchMyPageResponse

    @PATCH("/students/profile")
    @RequiresAccessToken
    abstract suspend fun editProfile(@Body request: EditProfileRequest)

    @DELETE("/students")
    @RequiresAccessToken
    abstract suspend fun withdraw()
}
