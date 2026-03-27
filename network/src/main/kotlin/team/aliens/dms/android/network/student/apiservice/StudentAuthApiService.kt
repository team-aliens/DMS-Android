package team.aliens.dms.android.network.student.apiservice

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import team.aliens.dms.android.network.student.model.EditPasswordRequest
import team.aliens.dms.android.network.student.model.ExamineStudentNumberResponse
import team.aliens.dms.android.network.student.model.FindIdResponse
import team.aliens.dms.android.network.student.model.SignUpRequest
import team.aliens.dms.android.network.student.model.SignUpResponse
import java.util.UUID

internal interface StudentAuthApiService {

    @POST("/students/signup")
    suspend fun signUp(@Body request: SignUpRequest): SignUpResponse

    @GET("/students/name")
    suspend fun examineStudentNumber(
        @Query("school_id") schoolId: UUID,
        @Query("grade") grade: Int,
        @Query("class_room") classRoom: Int,
        @Query("number") number: Int,
    ): ExamineStudentNumberResponse

    @GET("/students/account-id/{school-id}")
    suspend fun findId(
        @Path("school-id") schoolId: UUID,
        @Query("name") studentName: String,
        @Query("grade") grade: Int,
        @Query("class_room") classRoom: Int,
        @Query("number") number: Int,
    ): FindIdResponse

    @PATCH("/students/password/initialization")
    suspend fun editPassword(@Body request: EditPasswordRequest)

    @GET("/students/account-id/duplication")
    suspend fun checkIdDuplication(@Query("account_id") accountId: String)

    @GET("/students/email/duplication")
    suspend fun checkEmailDuplication(@Query("email") email: String)
}
