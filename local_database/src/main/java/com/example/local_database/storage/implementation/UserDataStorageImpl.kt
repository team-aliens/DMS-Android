package com.example.local_database.storage.implementation

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.local_database.param.FeaturesParam
import com.example.local_database.param.UserPersonalKeyParam
import com.example.local_database.storage.declaration.UserDataStorage
import com.example.local_database.storage.implementation.UserDataStorageImpl.UserPersonalKey.ACCESS_TOKEN
import com.example.local_database.storage.implementation.UserDataStorageImpl.UserPersonalKey.ACCESS_TOKEN_EXPIRED_AT
import com.example.local_database.storage.implementation.UserDataStorageImpl.UserPersonalKey.REFRESH_TOKEN
import com.example.local_database.storage.implementation.UserDataStorageImpl.UserPersonalKey.REFRESH_TOKEN_EXPIRED_AT
import com.example.local_database.storage.implementation.UserDataStorageImpl.UserVisible.MEAL
import com.example.local_database.storage.implementation.UserDataStorageImpl.UserVisible.NOTICE
import com.example.local_database.storage.implementation.UserDataStorageImpl.UserVisible.POINT
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UserDataStorageImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : UserDataStorage {

    @SuppressLint("CommitPrefEdits")
    override fun setPersonalKey(personalKeyParam: UserPersonalKeyParam) {
        setString(context, ACCESS_TOKEN, personalKeyParam.accessToken)
        setString(
            context,
            ACCESS_TOKEN_EXPIRED_AT,
            personalKeyParam.accessTokenExpiredAt.toString()
        )
        setString(context, REFRESH_TOKEN, personalKeyParam.refreshToken)
        setString(
            context,
            REFRESH_TOKEN_EXPIRED_AT,
            personalKeyParam.refreshTokenExpiredAt.toString()
        )
    }

    override fun fetchAccessToken(): String =
        getString(context, ACCESS_TOKEN)!!

    override fun fetchAccessTokenExpiredAt(): String {
        Log.d("123123", getString(context, ACCESS_TOKEN_EXPIRED_AT)!!   )
        return getString(context, ACCESS_TOKEN_EXPIRED_AT)!!
    }

    override fun fetchRefreshToken(): String =
        getString(context, REFRESH_TOKEN)!!

    override fun fetchRefreshTokenExpiredAt(): String =
        getString(context, REFRESH_TOKEN_EXPIRED_AT)!!

    override fun clearToken() {
        setString(context, ACCESS_TOKEN, "")
        setString(context, ACCESS_TOKEN_EXPIRED_AT, "")
        setString(context, REFRESH_TOKEN, "")
        setString(context, REFRESH_TOKEN_EXPIRED_AT, "")
    }

    override fun setUserVisible(featuresParam: FeaturesParam) {
            setBoolean(context, MEAL, featuresParam.mealService)
            setBoolean(context, NOTICE, featuresParam.noticeService)
            setBoolean(context, POINT, featuresParam.pointService)
    }

    override fun fetchMealServiceBoolean(): Boolean =
        getBoolean(context, MEAL)

    override fun fetchNoticeServiceBoolean(): Boolean =
        getBoolean(context, NOTICE)

    override fun fetchPointServiceBoolean(): Boolean =
        getBoolean(context, POINT)

    private fun getPreferences(key: String?, context: Context): SharedPreferences? {
        return context.getSharedPreferences(key, Context.MODE_PRIVATE)
    }

    private fun setString(context: Context?, key: String?, value: String?) {
        val prefs: SharedPreferences = context?.let { getPreferences(key, it) }!!
        val editor = prefs.edit()
        editor.putString(key, value)
        editor.apply()
    }

    private fun setBoolean(context: Context?, key: String?, value: Boolean) {
        val prefs = context?.let { getPreferences(key, it) }
        val editor = prefs!!.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    private fun getString(context: Context?, key: String?): String? {
        val prefs = context?.let { getPreferences(key, it) }
        return prefs!!.getString(key, "")
    }

    private fun getBoolean(context: Context?, key: String?): Boolean {
        val prefs = context?.let { getPreferences(key, it) }
        return prefs!!.getBoolean(key, false)
    }

    private object UserPersonalKey {
        const val ACCESS_TOKEN = "ACCESS_TOKEN"
        const val ACCESS_TOKEN_EXPIRED_AT = "ACCESS_TOKEN_EXPIRED_AT"
        const val REFRESH_TOKEN = "REFRESH_TOKEN"
        const val REFRESH_TOKEN_EXPIRED_AT = "REFRESH_TOKEN_EXPIRED_AT"
    }

    private object UserVisible {
        const val MEAL = "MEAL"
        const val NOTICE = "NOTICE"
        const val POINT = "POINT"
    }
}
