package team.aliens.local.datasource

import team.aliens.data.datasource.local.LocalSchoolDataSource
import team.aliens.domain.model.student.Features
import team.aliens.local.datastore.storage.SchoolDataStorage
import javax.inject.Inject

class LocalSchoolDataSourceImpl @Inject constructor(
    private val schoolDataStorage: SchoolDataStorage,
) : LocalSchoolDataSource {

    override suspend fun findFeature(): Features {
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

    override suspend fun saveFeatures(
        features: Features,
    ) {
        schoolDataStorage.saveFeature(
            features = features,
        )
    }
}
