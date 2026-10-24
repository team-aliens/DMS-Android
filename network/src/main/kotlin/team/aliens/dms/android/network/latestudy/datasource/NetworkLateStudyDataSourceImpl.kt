package team.aliens.dms.android.network.latestudy.datasource

import team.aliens.dms.android.core.network.util.handleNetworkRequest
import team.aliens.dms.android.network.latestudy.apiservice.LateStudyApiService
import team.aliens.dms.android.network.latestudy.model.FetchStudyTypesResponse
import team.aliens.dms.android.network.latestudy.model.FetchTeachersResponse
import team.aliens.dms.android.network.latestudy.model.StudyApplicationStatusResponse
import team.aliens.dms.android.network.latestudy.model.SubmitLateStudyRequest
import retrofit2.HttpException
import javax.inject.Inject

class NetworkLateStudyDataSourceImpl @Inject constructor(
    private val lateStudyApiService: LateStudyApiService,
) : NetworkLateStudyDataSource {

    override suspend fun fetchStudyTypes(): FetchStudyTypesResponse =
        lateStudyApiService.fetchStudyTypes()

    override suspend fun fetchTeachers(): FetchTeachersResponse =
        lateStudyApiService.fetchTeachers()

    override suspend fun fetchMyStudyApplicationStatus(): StudyApplicationStatusResponse? =
        try {
            lateStudyApiService.fetchMyStudyApplicationStatus()
        } catch (exception: HttpException) {
            if (exception.code() == 404) null else throw exception
        }

    override suspend fun cancelMyStudyApplication(): Unit =
        handleNetworkRequest {
            lateStudyApiService.cancelMyStudyApplication()
        }

    override suspend fun submitLateStudy(request: SubmitLateStudyRequest): Unit =
        handleNetworkRequest {
            lateStudyApiService.submitLateStudy(request)
        }
}
