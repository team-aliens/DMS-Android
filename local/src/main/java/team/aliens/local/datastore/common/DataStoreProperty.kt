package team.aliens.local.datastore.common

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object DataStoreProperty {

    object DataStore {
        const val Auth = "auth_prefs"
    }

    object Key {

        object Auth {

            val AccessToken = stringPreferencesKey("access_token")
            val AccessTokenExpiredAt = stringPreferencesKey("access_token_expired_at")
            val RefreshToken = stringPreferencesKey("refresh_token")
            val RefreshTokenExpiredAt = stringPreferencesKey("refresh_token_expired_at")
            val AutoSignIn = booleanPreferencesKey("auto_sign_in")
        }

        object Student {

            val MealService = booleanPreferencesKey("meal_service")
            val NoticeService = booleanPreferencesKey("notice_service")
            val PointService = booleanPreferencesKey("point_service")
            val StudyRoomService = booleanPreferencesKey("study_room_service")
            val RemainsService = booleanPreferencesKey("remains_service")
        }
    }
}
