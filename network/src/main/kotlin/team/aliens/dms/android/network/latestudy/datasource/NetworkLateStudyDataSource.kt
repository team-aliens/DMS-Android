package team.aliens.dms.android.network.latestudy.datasource

import team.aliens.dms.android.network.latestudy.model.FetchStudyTypesResponse

interface NetworkLateStudyDataSource {
    suspend fun fetchStudyTypes(): FetchStudyTypesResponse
}
