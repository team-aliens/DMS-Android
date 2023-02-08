package team.aliens.data.remote.api

import com.example.data.remote.request.students.ResetPasswordRequest
import com.example.data.remote.request.students.SignUpRequest
import com.example.data.remote.response.students.ExamineGradeResponse
import com.example.data.remote.url.DmsUrl
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.UUID

interface StudentsApi {
    @POST(DmsUrl.Students.register)
    suspend fun postRegister(
        @Body signUpRequest: SignUpRequest,
    )

    @GET(DmsUrl.Students.duplicateCheckId)
    suspend fun duplicateCheckId(
        @Query("account_id") accountId: String
    )

    @GET(DmsUrl.Students.duplicateCheckEmail)
    suspend fun duplicateCheckEmail(
        @Query("email") email: String
    )

    @PATCH(DmsUrl.Students.resetPassword)
    suspend fun resetPassword(
        @Body resetPasswordRequest: ResetPasswordRequest
    )

    @GET(DmsUrl.Students.examineGrade)
    suspend fun examineGrade(
        @Query("school_id") schoolId: UUID,
        @Query("grade") grade: Int,
        @Query("class_room") classRoom: Int,
        @Query("number") number: Int,
    ): ExamineGradeResponse
}