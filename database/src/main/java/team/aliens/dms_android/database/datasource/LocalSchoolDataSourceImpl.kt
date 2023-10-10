package team.aliens.dms_android.database.datasource

import team.aliens.data.datasource.local.LocalSchoolDataSource
import team.aliens.dms_android.domain.model.student.Features
import team.aliens.dms_android.database.datastore.storage.SchoolDataStorage
import javax.inject.Inject

class LocalSchoolDataSourceImpl @Inject constructor(
    private val schoolDataStorage: SchoolDataStorage,
) : LocalSchoolDataSource {

    override suspend fun findFeatures(): Features {
        return schoolDataStorage.findFeatures()
    }

    override suspend fun findMealFeatureEnabled(): Boolean {
        return schoolDataStorage.findMealFeatureEnabled()
    }

    override suspend fun findNoticeFeatureEnabled(): Boolean {
        return schoolDataStorage.findNoticeFeatureEnabled()
    }

    override suspend fun findPointServiceEnabled(): Boolean {
        return schoolDataStorage.findPointServiceEnabled()
    }

    override suspend fun findStudyRoomServiceEnabled(): Boolean {
        return schoolDataStorage.findStudyRoomServiceEnabled()
    }

    override suspend fun findRemainsServiceEnabled(): Boolean {
        return schoolDataStorage.findRemainsServiceEnabled()
    }

    override suspend fun saveFeatures(
        features: Features,
    ) {
        schoolDataStorage.saveFeatures(
            features = features,
        )
    }
}
