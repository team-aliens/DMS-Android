package team.aliens.dms.android.data.latestudy.repository

import team.aliens.dms.android.data.latestudy.mapper.toModel
import team.aliens.dms.android.data.latestudy.model.StudyType
import team.aliens.dms.android.network.latestudy.datasource.NetworkLateStudyDataSource
import TeacherResponse
import team.aliens.dms.android.network.latestudy.model.SubmitLateStudyRequest
import javax.inject.Inject

class LateStudyRepositoryImpl @Inject constructor(
    private val networkLateStudyDataSource: NetworkLateStudyDataSource,
) : LateStudyRepository {

    override suspend fun fetchStudyTypes(): List<StudyType> =
        networkLateStudyDataSource.fetchStudyTypes().toModel()

    override suspend fun fetchTeachers(): List<TeacherResponse> =
        networkLateStudyDataSource.fetchTeachers()

    override suspend fun submitLateStudy(request: SubmitLateStudyRequest) {
        networkLateStudyDataSource.submitLateStudy(request)
    }
}
