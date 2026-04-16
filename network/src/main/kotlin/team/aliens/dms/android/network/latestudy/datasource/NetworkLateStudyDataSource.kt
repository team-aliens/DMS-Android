package team.aliens.dms.android.network.latestudy.datasource

import team.aliens.dms.android.network.latestudy.model.FetchStudyTypesResponse
import team.aliens.dms.android.network.latestudy.model.response.FetchStudyTypesResponse

interface NetworkLateStudyDataSource {
    suspend fun fetchStudyTypes(): FetchStudyTypesResponse
}
