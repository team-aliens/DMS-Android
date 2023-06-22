package team.aliens.local.datastore.storage

import team.aliens.domain.model.student.Features

interface SchoolDataStorage {

    suspend fun findFeature(): Features

    suspend fun findMealFeatureEnabled(): Boolean

    suspend fun findNoticeFeatureEnabled(): Boolean

    suspend fun findPointServiceEnabled(): Boolean

    suspend fun findStudyRoomServiceEnabled(): Boolean

    suspend fun findRemainsServiceEnabled(): Boolean

    suspend fun saveFeature(
        features: Features,
    )
}
