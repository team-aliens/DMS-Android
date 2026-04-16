package team.aliens.dms.android.data.latestudy.repository

import team.aliens.dms.android.data.latestudy.mapper.toModel
import team.aliens.dms.android.domain.latestudy.model.StudyType
import team.aliens.dms.android.domain.latestudy.repository.LateStudyRepository
import team.aliens.dms.android.network.latestudy.datasource.NetworkLateStudyDataSource
import javax.inject.Inject

class LateStudyRepositoryImpl @Inject constructor(
    private val networkLateStudyDataSource: NetworkLateStudyDataSource,
) : LateStudyRepository {
    override suspend fun fetchStudyTypes(): List<StudyType> =
        networkLateStudyDataSource.fetchStudyTypes().toModel()
}
