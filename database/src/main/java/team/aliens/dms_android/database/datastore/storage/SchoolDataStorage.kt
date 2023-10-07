package team.aliens.dms_android.database.datastore.storage

import team.aliens.domain.model.student.Features

interface SchoolDataStorage {

    suspend fun findFeatures(): Features

    suspend fun findMealFeatureEnabled(): Boolean

    suspend fun findNoticeFeatureEnabled(): Boolean

    suspend fun findPointServiceEnabled(): Boolean

    suspend fun findStudyRoomServiceEnabled(): Boolean

    suspend fun findRemainsServiceEnabled(): Boolean

    suspend fun saveFeatures(features: Features)
}