package team.aliens.local.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import team.aliens.data._datasource.local.LocalSchoolDataSource
import team.aliens.domain._model.student.Feature
import javax.inject.Inject

class LocalSchoolDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : LocalSchoolDataSource {

    override suspend fun findFeature(): Feature {
        TODO("Not yet implemented")
    }

    override suspend fun findMealFeatureEnabled(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun findNoticeFeatureEnabled(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun findPointServiceEnabled(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun findStudyRoomServiceEnabled(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun findRemainsServiceEnabled(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun saveFeature(
        feature: Feature,
    ) {
        TODO("Not yet implemented")
    }
}
