package team.aliens.dms.android.network.latestudy.datasource

import team.aliens.dms.android.network.latestudy.apiservice.LateStudyApiService
import team.aliens.dms.android.network.latestudy.model.FetchStudyTypesResponse
import team.aliens.dms.android.network.latestudy.model.FetchTeachersResponse
import team.aliens.dms.android.network.latestudy.model.StudyApplicationStatusResponse
import team.aliens.dms.android.network.latestudy.model.SubmitLateStudyRequest
import javax.inject.Inject

class NetworkLateStudyDataSourceImpl @Inject constructor(
    private val lateStudyApiService: LateStudyApiService,
) : NetworkLateStudyDataSource {

    override suspend fun fetchStudyTypes(): FetchStudyTypesResponse =
        lateStudyApiService.fetchStudyTypes()

    override suspend fun fetchTeachers(): FetchTeachersResponse =
        lateStudyApiService.fetchTeachers()

    override suspend fun fetchMyStudyApplicationStatus(): StudyApplicationStatusResponse =
        lateStudyApiService.fetchMyStudyApplicationStatus()

    override suspend fun submitLateStudy(request: SubmitLateStudyRequest) {
        lateStudyApiService.submitLateStudy(request)
    }
}
