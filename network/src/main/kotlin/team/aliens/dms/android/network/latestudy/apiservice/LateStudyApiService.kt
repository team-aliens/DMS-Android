package team.aliens.dms.android.network.latestudy.apiservice

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import team.aliens.dms.android.network.latestudy.model.FetchStudyTypesResponse
import team.aliens.dms.android.network.latestudy.model.FetchTeachersResponse
import team.aliens.dms.android.network.latestudy.model.StudyApplicationStatusResponse
import team.aliens.dms.android.network.latestudy.model.SubmitLateStudyRequest

interface LateStudyApiService {
    @GET("/daybreaks/study-type")
    suspend fun fetchStudyTypes(): FetchStudyTypesResponse

    @GET("/daybreaks/study-application/my")
    suspend fun fetchMyStudyApplicationStatus(): StudyApplicationStatusResponse

    @GET("/teachers/general")
    suspend fun fetchTeachers(): FetchTeachersResponse

    @POST("/daybreaks/study-application")
    suspend fun submitLateStudy(
        @Body request: SubmitLateStudyRequest
    )
}
