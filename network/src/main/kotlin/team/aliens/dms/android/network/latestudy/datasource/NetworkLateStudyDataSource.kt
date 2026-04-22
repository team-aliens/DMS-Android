package team.aliens.dms.android.network.latestudy.datasource

import team.aliens.dms.android.network.latestudy.model.FetchStudyTypesResponse
import team.aliens.dms.android.network.latestudy.model.FetchTeachersResponse
import team.aliens.dms.android.network.latestudy.model.StudyApplicationStatusResponse
import team.aliens.dms.android.network.latestudy.model.SubmitLateStudyRequest

interface NetworkLateStudyDataSource {
    suspend fun fetchStudyTypes(): FetchStudyTypesResponse
    suspend fun fetchTeachers(): FetchTeachersResponse
    suspend fun fetchMyStudyApplicationStatus(): StudyApplicationStatusResponse
    suspend fun submitLateStudy(request: SubmitLateStudyRequest)
}
