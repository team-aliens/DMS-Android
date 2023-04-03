package team.aliens.local.datastore.storage

import team.aliens.domain._model.student.Feature

interface StudentDataStorage {

    suspend fun findFeature(): Feature

    suspend fun findMealFeatureEnabled(): Boolean

    suspend fun findNoticeFeatureEnabled(): Boolean

    suspend fun findPointServiceEnabled(): Boolean

    suspend fun findStudyRoomServiceEnabled(): Boolean

    suspend fun findRemainsServiceEnabled(): Boolean

    suspend fun saveFeature(
        feature: Feature,
    )
}
