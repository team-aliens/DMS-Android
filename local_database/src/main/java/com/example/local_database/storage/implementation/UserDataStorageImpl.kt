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
    @ApplicationContext private val context: Context
): UserDataStorage {
    
    override fun setUserVisible(userVisibleParam: UserVisibleParam) {
        getSharedPreference().edit().let {
            it.putBoolean(SURVEY.toString(), userVisibleParam.surveyBoolean)
            it.putBoolean(NOTICE.toString(), userVisibleParam.noticeBoolean)
            it.putBoolean(MYPAGE.toString(), userVisibleParam.myPageBoolean)
            it.putBoolean(RECENTROOM.toString(), userVisibleParam.recentRoomBoolean)
        }
    }

    override fun fetchSurveyBoolean(): Boolean =
        getSharedPreference().getBoolean(SURVEY.toString(), false)

    override fun fetchNoticeBoolean(): Boolean =
        getSharedPreference().getBoolean(NOTICE.toString(), false)

    override fun fetchMyPageBoolean(): Boolean =
        getSharedPreference().getBoolean(MYPAGE.toString(), false)

    override fun fetchRecentRoomBoolean(): Boolean =
        getSharedPreference().getBoolean(RECENTROOM.toString(), false)

    private fun getSharedPreference() =
        PreferenceManager.getDefaultSharedPreferences(context)

    private object UserVisible {
        const val SURVEY = false
        const val NOTICE = false
        const val MYPAGE = false
        const val RECENTROOM = false
    }
}