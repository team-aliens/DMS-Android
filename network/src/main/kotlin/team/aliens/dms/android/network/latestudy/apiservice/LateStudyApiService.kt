package team.aliens.dms.android.network.latestudy.apiservice

import TeacherResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import team.aliens.dms.android.network.latestudy.model.FetchStudyTypesResponse
import team.aliens.dms.android.network.latestudy.model.SubmitLateStudyRequest

interface LateStudyApiService {
    @GET("/study-type")
    suspend fun fetchStudyTypes(): FetchStudyTypesResponse

    @GET("/teachers/general")
    suspend fun fetchTeachers(): List<TeacherResponse>

    @POST("/study-application")
    suspend fun submitLateStudy(
        @Body request: SubmitLateStudyRequest
    )
}
