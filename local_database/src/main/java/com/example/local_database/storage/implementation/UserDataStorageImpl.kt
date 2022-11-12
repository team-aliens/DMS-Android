package com.example.local_database.storage.implementation

import android.content.Context
import android.preference.PreferenceManager
import com.example.local_database.param.FeaturesParam
import com.example.local_database.param.UserPersonalKeyParam
import com.example.local_database.storage.declaration.UserDataStorage
import com.example.local_database.storage.implementation.UserDataStorageImpl.UserPersonalKey.ACCESS_TOKEN
import com.example.local_database.storage.implementation.UserDataStorageImpl.UserPersonalKey.EXPIRED_AT
import com.example.local_database.storage.implementation.UserDataStorageImpl.UserPersonalKey.REFRESH_TOKEN
import com.example.local_database.storage.implementation.UserDataStorageImpl.UserVisible.MEAL
import com.example.local_database.storage.implementation.UserDataStorageImpl.UserVisible.NOTICE
import com.example.local_database.storage.implementation.UserDataStorageImpl.UserVisible.POINT
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@Suppress("DEPRECATION")
class UserDataStorageImpl @Inject constructor(
    @ApplicationContext private val context: Context,
): UserDataStorage {

    override fun setPersonalKey(personalKeyParam: UserPersonalKeyParam) {
        getSharedPreference().edit().let {
            it.putString(ACCESS_TOKEN, personalKeyParam.accessToken)
            it.putString(EXPIRED_AT, personalKeyParam.expiredAt)
            it.putString(REFRESH_TOKEN, personalKeyParam.refreshToken)
        }
    }

    override fun fetchAccessToken(): String =
        getSharedPreference().getString(ACCESS_TOKEN, "")!!

    override fun fetchExpiredAt(): String =
        getSharedPreference().getString(EXPIRED_AT, "")!!

    override fun fetchRefreshToken(): String =
        getSharedPreference().getString(REFRESH_TOKEN, "")!!

    override fun setUserVisible(featuresParam: FeaturesParam) {
        getSharedPreference().edit().let {
            it.putBoolean(MEAL, featuresParam.mealService)
            it.putBoolean(NOTICE, featuresParam.noticeService)
            it.putBoolean(POINT, featuresParam.pointService)
        }
    }

    override fun fetchMealServiceBoolean(): Boolean =
        getSharedPreference().getBoolean(MEAL, false)

    override fun fetchNoticeServiceBoolean(): Boolean =
        getSharedPreference().getBoolean(NOTICE, false)

    override fun fetchPointServiceBoolean(): Boolean =
        getSharedPreference().getBoolean(POINT, false)

    private fun getSharedPreference() =
        PreferenceManager.getDefaultSharedPreferences(context)

    private object UserPersonalKey {
        const val ACCESS_TOKEN = "ACCESS_TOKEN"
        const val EXPIRED_AT = "EXPIRED_AT"
        const val REFRESH_TOKEN = "REFRESH_TOKEN"
    }

    private object UserVisible {
        const val MEAL = "MEAL"
        const val NOTICE = "NOTICE"
        const val POINT = "POINT"
    }
}
