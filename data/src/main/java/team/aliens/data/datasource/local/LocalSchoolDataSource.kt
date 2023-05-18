package team.aliens.data.datasource.local

import team.aliens.domain._model.student.Feature

interface LocalSchoolDataSource {

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
