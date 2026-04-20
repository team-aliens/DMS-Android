package team.aliens.dms.android.network.latestudy.apiservice

import TeacherResponse
import retrofit2.http.GET
import team.aliens.dms.android.network.latestudy.model.FetchStudyTypesResponse

interface LateStudyApiService {
    @GET("/study-type")
    suspend fun fetchStudyTypes(): FetchStudyTypesResponse

    @GET("/teachers/general")
    suspend fun fetchTeachers(): List<TeacherResponse>
}
