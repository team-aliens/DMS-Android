package team.aliens.dms.android.network.latestudy.datasource

import team.aliens.dms.android.network.latestudy.apiservice.LateStudyApiService
import team.aliens.dms.android.network.latestudy.model.FetchStudyTypesResponse

import javax.inject.Inject

class NetworkLateStudyDataSourceImpl @Inject constructor(
    private val lateStudyApiService: LateStudyApiService,
) : NetworkLateStudyDataSource {
    override suspend fun fetchStudyTypes(): FetchStudyTypesResponse =
        lateStudyApiService.fetchStudyTypes()
}
