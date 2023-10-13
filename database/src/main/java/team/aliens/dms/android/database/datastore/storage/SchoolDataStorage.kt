package team.aliens.dms.android.database.datastore.storage

import team.aliens.dms.android.domain.model.student.Features

// TODO: remove
@Deprecated("No usage")
interface SchoolDataStorage {

    suspend fun findFeatures(): Features

    suspend fun findMealFeatureEnabled(): Boolean

    suspend fun findNoticeFeatureEnabled(): Boolean

    suspend fun findPointServiceEnabled(): Boolean

    suspend fun findStudyRoomServiceEnabled(): Boolean

    suspend fun findRemainsServiceEnabled(): Boolean

    suspend fun saveFeatures(features: Features)
}
