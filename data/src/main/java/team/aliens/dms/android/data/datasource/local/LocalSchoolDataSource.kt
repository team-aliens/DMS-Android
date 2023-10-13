package team.aliens.dms.android.data.datasource.local

import team.aliens.dms.android.domain.model.student.Features

interface LocalSchoolDataSource {

    suspend fun findFeatures(): Features

    suspend fun findMealFeatureEnabled(): Boolean

    suspend fun findNoticeFeatureEnabled(): Boolean

    suspend fun findPointServiceEnabled(): Boolean

    suspend fun findStudyRoomServiceEnabled(): Boolean

    suspend fun findRemainsServiceEnabled(): Boolean

    suspend fun saveFeatures(features: Features)
}
