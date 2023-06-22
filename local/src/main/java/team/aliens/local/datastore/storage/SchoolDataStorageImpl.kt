package team.aliens.local.datastore.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first
import team.aliens.domain.model.student.Features
import team.aliens.local.datastore.common.DataStoreProperty.Key.Student.MealService
import team.aliens.local.datastore.common.DataStoreProperty.Key.Student.NoticeService
import team.aliens.local.datastore.common.DataStoreProperty.Key.Student.PointService
import team.aliens.local.datastore.common.DataStoreProperty.Key.Student.RemainsService
import team.aliens.local.datastore.common.DataStoreProperty.Key.Student.StudyRoomService
import javax.inject.Inject

class SchoolDataStorageImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : SchoolDataStorage {

    override suspend fun findFeature(): Features {

        val fetchedMealOption = this.findMealFeatureEnabled()
        val fetchedNoticeOption = this.findNoticeFeatureEnabled()
        val fetchedPointOption = this.findPointServiceEnabled()
        val fetchedStudyRoomOption = this.findStudyRoomServiceEnabled()
        val fetchedRemainsOption = this.findRemainsServiceEnabled()

        return Features(
            mealService = fetchedMealOption,
            noticeService = fetchedNoticeOption,
            pointService = fetchedPointOption,
            studyRoomService = fetchedStudyRoomOption,
            remainsService = fetchedRemainsOption,
        )
    }

    override suspend fun findMealFeatureEnabled(): Boolean {
        return dataStore.data.first()[MealService] ?: false
    }

    override suspend fun findNoticeFeatureEnabled(): Boolean {
        return dataStore.data.first()[NoticeService] ?: false
    }

    override suspend fun findPointServiceEnabled(): Boolean {
        return dataStore.data.first()[PointService] ?: false
    }

    override suspend fun findStudyRoomServiceEnabled(): Boolean {
        return dataStore.data.first()[StudyRoomService] ?: false
    }

    override suspend fun findRemainsServiceEnabled(): Boolean {
        return dataStore.data.first()[RemainsService] ?: false
    }

    override suspend fun saveFeature(
        features: Features,
    ) {
        dataStore.edit {
            it[MealService] = features.mealService
            it[NoticeService] = features.noticeService
            it[PointService] = features.pointService
            it[StudyRoomService] = features.studyRoomService
            it[RemainsService] = features.remainsService
        }
    }
}
