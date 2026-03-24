package team.aliens.dms.android.network.student.apiservice

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import team.aliens.dms.android.core.jwt.RequiresAccessToken
import team.aliens.dms.android.network.student.model.EditProfileRequest
import team.aliens.dms.android.network.student.model.FetchMyPageResponse
import team.aliens.dms.android.network.student.model.FetchStudentsResponse

internal interface StudentProfileApiService {

    @GET("/students/profile")
    @RequiresAccessToken
    suspend fun fetchMyPage(): FetchMyPageResponse

    @PATCH("/students/profile")
    @RequiresAccessToken
    suspend fun editProfile(@Body request: EditProfileRequest)

    @DELETE("/students")
    @RequiresAccessToken
    suspend fun withdraw()

    @GET("/students")
    suspend fun fetchStudents(): FetchStudentsResponse
}
