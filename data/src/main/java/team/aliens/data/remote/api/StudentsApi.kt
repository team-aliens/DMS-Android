package team.aliens.data.remote.api

import retrofit2.Response
import retrofit2.http.*
import team.aliens.data.remote.request.students.EditProfileImageRequest
import team.aliens.data.remote.request.students.ResetPasswordRequest
import team.aliens.data.remote.request.students.SignUpRequest
import team.aliens.data.remote.response.students.ExamineGradeResponse
import team.aliens.data.remote.url.DmsUrl
import java.util.*

interface StudentsApi {
    @POST(DmsUrl.Students.register)
    suspend fun postRegister(
        @Body signUpRequest: SignUpRequest,
    )

    @GET(DmsUrl.Students.duplicateCheckId)
    suspend fun duplicateCheckId(
        @Query("account_id") accountId: String,
    )

    @GET(DmsUrl.Students.duplicateCheckEmail)
    suspend fun duplicateCheckEmail(
        @Query("email") email: String,
    )

    @PATCH(DmsUrl.Students.resetPassword)
    suspend fun resetPassword(
        @Body resetPasswordRequest: ResetPasswordRequest,
    )

    @GET(DmsUrl.Students.examineGrade)
    suspend fun examineGrade(
        @Query("school_id") schoolId: UUID,
        @Query("grade") grade: Int,
        @Query("class_room") classRoom: Int,
        @Query("number") number: Int,
    ): ExamineGradeResponse

    @PATCH(DmsUrl.Students.editProfileImage)
    suspend fun editProfileImage(
        @Body editProfileImageRequest: EditProfileImageRequest,
    ): Response<Unit>
}