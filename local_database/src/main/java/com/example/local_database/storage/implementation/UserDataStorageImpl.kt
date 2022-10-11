package com.example.local_database.storage.implementation

import android.content.Context
import android.preference.PreferenceManager
import com.example.local_database.param.UserVisibleParam
import com.example.local_database.storage.declaration.UserDataStorage
import com.example.local_database.storage.implementation.UserDataStorageImpl.UserVisible.MYPAGE
import com.example.local_database.storage.implementation.UserDataStorageImpl.UserVisible.NOTICE
import com.example.local_database.storage.implementation.UserDataStorageImpl.UserVisible.RECENTROOM
import com.example.local_database.storage.implementation.UserDataStorageImpl.UserVisible.SURVEY
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UserDataStorageImpl @Inject constructor(
    @ApplicationContext private val context: Context,
): UserDataStorage {

    override fun setUserVisible(userVisibleParam: UserVisibleParam) {
        getSharedPreference().edit().let {
            it.putBoolean(SURVEY, userVisibleParam.surveyBoolean)
            it.putBoolean(NOTICE, userVisibleParam.noticeBoolean)
            it.putBoolean(MYPAGE, userVisibleParam.myPageBoolean)
            it.putBoolean(RECENTROOM, userVisibleParam.recentRoomBoolean)
        }
    }

    override fun fetchSurveyBoolean(): Boolean =
        getSharedPreference().getBoolean(SURVEY, false)

    override fun fetchNoticeBoolean(): Boolean =
        getSharedPreference().getBoolean(NOTICE, false)

    override fun fetchMyPageBoolean(): Boolean =
        getSharedPreference().getBoolean(MYPAGE, false)

    override fun fetchRecentRoomBoolean(): Boolean =
        getSharedPreference().getBoolean(RECENTROOM, false)

    private fun getSharedPreference() =
        PreferenceManager.getDefaultSharedPreferences(context)

    private object UserVisible {
        const val SURVEY = "SURVEY"
        const val NOTICE = "NOTICE"
        const val MYPAGE = "MYPAGE"
        const val RECENTROOM = "RECENTROOM"
    }

}