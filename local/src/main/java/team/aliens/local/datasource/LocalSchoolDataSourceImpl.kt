package team.aliens.local.datasource

import team.aliens.data._datasource.local.LocalSchoolDataSource
import team.aliens.domain._model.student.Feature
import team.aliens.local.datastore.storage.SchoolDataStorage
import javax.inject.Inject

class LocalSchoolDataSourceImpl @Inject constructor(
    private val schoolDataStorage: SchoolDataStorage,
) : LocalSchoolDataSource {

    override suspend fun findFeature(): Feature {
        return schoolDataStorage.findFeature()
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

    override suspend fun saveFeature(
        feature: Feature,
    ) {
        schoolDataStorage.saveFeature(
            feature = feature,
        )
    }
}
