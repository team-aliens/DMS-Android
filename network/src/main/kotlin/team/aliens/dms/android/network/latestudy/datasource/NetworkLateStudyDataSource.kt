package team.aliens.dms.android.network.latestudy.datasource

import TeacherResponse
import team.aliens.dms.android.network.latestudy.model.FetchStudyTypesResponse
import team.aliens.dms.android.network.latestudy.model.SubmitLateStudyRequest

interface NetworkLateStudyDataSource {
    suspend fun fetchStudyTypes(): FetchStudyTypesResponse
    suspend fun fetchTeachers(): List<TeacherResponse>
    suspend fun submitLateStudy(request: SubmitLateStudyRequest)
}
