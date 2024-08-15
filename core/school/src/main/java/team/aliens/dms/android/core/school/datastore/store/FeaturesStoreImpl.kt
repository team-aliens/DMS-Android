package team.aliens.dms.android.core.school.datastore.store

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import team.aliens.dms.android.core.datastore.FeaturesDataStore
import team.aliens.dms.android.core.datastore.PreferencesDataStore
import team.aliens.dms.android.core.datastore.util.transform
import team.aliens.dms.android.core.school.Features
import team.aliens.dms.android.core.school.exception.CannotStoreFeaturesException
import team.aliens.dms.android.core.school.exception.FeaturesNotFoundException
import javax.inject.Inject

internal class FeaturesStoreImpl @Inject constructor(
    @FeaturesDataStore private val featuresDataStore: PreferencesDataStore,
) : FeaturesStore() {
    override fun loadFeatures(): Features = runBlocking {
        featuresDataStore.data.map { preferences ->
            val mealService = preferences[MEAL_SERVICE] ?: throw FeaturesNotFoundException()
            val noticeService = preferences[NOTICE_SERVICE] ?: throw FeaturesNotFoundException()
            val pointService = preferences[POINT_SERVICE] ?: throw FeaturesNotFoundException()
            val studyRoomService =
                preferences[STUDY_ROOM_SERVICE] ?: throw FeaturesNotFoundException()
            val remainsService = preferences[REMAINS_SERVICE] ?: throw FeaturesNotFoundException()

            return@map Features(
                mealService = mealService,
                noticeService = noticeService,
                pointService = pointService,
                studyRoomService = studyRoomService,
                remainsService = remainsService,
            )
        }.first()
    }

    override suspend fun storeFeatures(features: Features) {
        transform(
            onFailure = { throw CannotStoreFeaturesException() },
        ) {
            featuresDataStore.edit { preferences ->
                preferences[MEAL_SERVICE] = features.mealService
                preferences[NOTICE_SERVICE] = features.noticeService
                preferences[POINT_SERVICE] = features.pointService
                preferences[STUDY_ROOM_SERVICE] = features.studyRoomService
                preferences[REMAINS_SERVICE] = features.remainsService
            }
        }
    }

    override suspend fun clearFeatures() {
        transform {
            featuresDataStore.edit { preferences -> preferences.clear() }
        }
    }

    private companion object {
        val MEAL_SERVICE = booleanPreferencesKey("meal-service")
        val NOTICE_SERVICE = booleanPreferencesKey("notice-service")
        val POINT_SERVICE = booleanPreferencesKey("point-service")
        val STUDY_ROOM_SERVICE = booleanPreferencesKey("study-room-service")
        val REMAINS_SERVICE = booleanPreferencesKey("remains-service")
    }
}
