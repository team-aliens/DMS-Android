package team.aliens.data.datasource.local

import team.aliens.domain.model.student.Features

interface LocalSchoolDataSource {

    suspend fun findFeature(): Features

    suspend fun findMealFeatureEnabled(): Boolean

    suspend fun findNoticeFeatureEnabled(): Boolean

    suspend fun findPointServiceEnabled(): Boolean

    suspend fun findStudyRoomServiceEnabled(): Boolean

    suspend fun findRemainsServiceEnabled(): Boolean

    suspend fun saveFeatures(features: Features)
}
