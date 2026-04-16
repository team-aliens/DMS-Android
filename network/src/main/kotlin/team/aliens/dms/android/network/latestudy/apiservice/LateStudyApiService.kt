package team.aliens.dms.android.network.latestudy.apiservice

import retrofit2.http.GET
import team.aliens.dms.android.network.latestudy.model.FetchStudyTypesResponse
import team.aliens.dms.android.network.latestudy.model.response.FetchStudyTypesResponse

interface LateStudyApiService {
    @GET("/study-type")
    suspend fun fetchStudyTypes(): FetchStudyTypesResponse
}
