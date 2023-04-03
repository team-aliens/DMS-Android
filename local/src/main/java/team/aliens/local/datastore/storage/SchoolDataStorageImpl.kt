package team.aliens.local.datastore.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.last
import team.aliens.domain._model.student.Feature
import team.aliens.local.datastore.common.DataStoreProperty.Key.Student.MealService
import team.aliens.local.datastore.common.DataStoreProperty.Key.Student.NoticeService
import team.aliens.local.datastore.common.DataStoreProperty.Key.Student.PointService
import team.aliens.local.datastore.common.DataStoreProperty.Key.Student.RemainsService
import team.aliens.local.datastore.common.DataStoreProperty.Key.Student.StudyRoomService
import javax.inject.Inject

class SchoolDataStorageImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : SchoolDataStorage {

    override suspend fun findFeature(): Feature {

        val fetchedMealOption = this.findMealFeatureEnabled()
        val fetchedNoticeOption = this.findNoticeFeatureEnabled()
        val fetchedPointOption = this.findPointServiceEnabled()
        val fetchedStudyRoomOption = this.findStudyRoomServiceEnabled()
        val fetchedRemainsOption = this.findRemainsServiceEnabled()

        return Feature(
            mealService = fetchedMealOption,
            noticeService = fetchedNoticeOption,
            pointService = fetchedPointOption,
            studyRoomService = fetchedStudyRoomOption,
            remainsService = fetchedRemainsOption,
        )
    }

    override suspend fun findMealFeatureEnabled(): Boolean {
        return dataStore.data.last()[MealService] ?: false
    }

    override suspend fun findNoticeFeatureEnabled(): Boolean {
        return dataStore.data.last()[NoticeService] ?: false
    }

    override suspend fun findPointServiceEnabled(): Boolean {
        return dataStore.data.last()[PointService] ?: false
    }

    override suspend fun findStudyRoomServiceEnabled(): Boolean {
        return dataStore.data.last()[StudyRoomService] ?: false
    }

    override suspend fun findRemainsServiceEnabled(): Boolean {
        return dataStore.data.last()[RemainsService] ?: false
    }

    override suspend fun saveFeature(
        feature: Feature,
    ) {
        dataStore.edit {
            it[MealService] = feature.mealService
            it[NoticeService] = feature.noticeService
            it[PointService] = feature.pointService
            it[StudyRoomService] = feature.studyRoomService
            it[RemainsService] = feature.remainsService
        }
    }
}
