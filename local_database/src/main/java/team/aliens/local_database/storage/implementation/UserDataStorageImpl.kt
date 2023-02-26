package team.aliens.local_database.storage.implementation

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import team.aliens.local_database.param.FeaturesParam
import team.aliens.local_database.param.UserPersonalKeyParam
import team.aliens.local_database.storage.declaration.UserDataStorage
import team.aliens.local_database.storage.implementation.UserDataStorageImpl.Companion.UserInfo.AUTO_SIGN_IN
import team.aliens.local_database.storage.implementation.UserDataStorageImpl.Companion.UserPersonalKey.ACCESS_TOKEN
import team.aliens.local_database.storage.implementation.UserDataStorageImpl.Companion.UserPersonalKey.ACCESS_TOKEN_EXPIRED_AT
import team.aliens.local_database.storage.implementation.UserDataStorageImpl.Companion.UserPersonalKey.REFRESH_TOKEN
import team.aliens.local_database.storage.implementation.UserDataStorageImpl.Companion.UserPersonalKey.REFRESH_TOKEN_EXPIRED_AT
import team.aliens.local_database.storage.implementation.UserDataStorageImpl.Companion.UserVisible.MEAL
import team.aliens.local_database.storage.implementation.UserDataStorageImpl.Companion.UserVisible.NOTICE
import team.aliens.local_database.storage.implementation.UserDataStorageImpl.Companion.UserVisible.POINT
import javax.inject.Inject

class UserDataStorageImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : UserDataStorage {

    private companion object {

        const val USER_DATA_STORAGE_KEY = "user_data_storage"

        object UserInfo {
            const val ID = "ID"
            const val PASSWORD = "PASSWORD"
            const val AUTO_SIGN_IN = "AUTO_SIGN_IN"
        }

        object UserPersonalKey {
            const val ACCESS_TOKEN = "ACCESS_TOKEN"
            const val ACCESS_TOKEN_EXPIRED_AT = "ACCESS_TOKEN_EXPIRED_AT"
            const val REFRESH_TOKEN = "REFRESH_TOKEN"
            const val REFRESH_TOKEN_EXPIRED_AT = "REFRESH_TOKEN_EXPIRED_AT"
        }

        object UserVisible {
            const val MEAL = "MEAL"
            const val NOTICE = "NOTICE"
            const val POINT = "POINT"
        }
    }

    private val prefs: SharedPreferences
        get() = context.getSharedPreferences(USER_DATA_STORAGE_KEY, Context.MODE_PRIVATE)
            ?: throw IllegalStateException()

    private val editor: SharedPreferences.Editor
        get() = prefs.edit()

    override fun setPersonalKey(personalKeyParam: UserPersonalKeyParam) {
        editor.run {
            putString(ACCESS_TOKEN, personalKeyParam.accessToken)
            putString(ACCESS_TOKEN_EXPIRED_AT, personalKeyParam.accessTokenExpiredAt.toString())
            putString(REFRESH_TOKEN, personalKeyParam.refreshToken)
            putString(REFRESH_TOKEN_EXPIRED_AT, personalKeyParam.refreshTokenExpiredAt.toString())
        }.apply()
    }

    override fun fetchAccessToken(): String {
        return prefs.getString(ACCESS_TOKEN, "")!!
    }

    override fun fetchAccessTokenExpiredAt(): String {
        return prefs.getString(ACCESS_TOKEN_EXPIRED_AT, "")!!
    }

    override fun fetchRefreshToken(): String {
        return prefs.getString(REFRESH_TOKEN, "")!!
    }

    override fun fetchRefreshTokenExpiredAt(): String {
        return prefs.getString(REFRESH_TOKEN_EXPIRED_AT, "")!!
    }

    override fun clearToken() {
        editor.clear().apply()
    }

    override fun setUserVisible(featuresParam: FeaturesParam) {
        editor.run {
            putBoolean(MEAL, featuresParam.mealService)
            putBoolean(NOTICE, featuresParam.noticeService)
            putBoolean(POINT, featuresParam.pointService)
        }.apply()
    }

    override fun fetchMealServiceBoolean(): Boolean {
        return prefs.getBoolean(MEAL, false)
    }

    override fun fetchNoticeServiceBoolean(): Boolean {
        return prefs.getBoolean(NOTICE, false)
    }

    override fun fetchPointServiceBoolean(): Boolean {
        return prefs.getBoolean(POINT, false)
    }

    override fun setAutoSignInOption(autoSignInEnabled: Boolean) {
        editor.putBoolean(AUTO_SIGN_IN, autoSignInEnabled).apply()
    }

    override fun fetchAutoSignInOption(): Boolean {
        return prefs.getBoolean(AUTO_SIGN_IN, false)
    }

    override fun signOut() {

        editor.putBoolean(AUTO_SIGN_IN, false).apply()

        clearToken()
    }
}
