package com.example.local_database.storage.implementation

import android.content.Context
import android.preference.PreferenceManager
import com.example.local_database.param.UserVisibleParam.FeaturesParam
import com.example.local_database.storage.declaration.UserDataStorage
import com.example.local_database.storage.implementation.UserDataStorageImpl.UserVisible.MEAL
import com.example.local_database.storage.implementation.UserDataStorageImpl.UserVisible.NOTICE
import com.example.local_database.storage.implementation.UserDataStorageImpl.UserVisible.POINT
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UserDataStorageImpl @Inject constructor(
    @ApplicationContext private val context: Context,
): UserDataStorage {

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

    private object UserVisible {
        const val MEAL = "MEAL"
        const val NOTICE = "NOTICE"
        const val POINT = "POINT"
    }
}
