package com.example.local_database.storage.implementation

import android.annotation.SuppressLint
import android.content.Context
import android.preference.PreferenceManager
import com.example.local_database.param.mypage.MyPageLocalParam
import com.example.local_database.storage.declaration.MyPageDataStorage
import com.example.local_database.storage.implementation.MyPageDataStorageImpl.MyPageValue.BONUS
import com.example.local_database.storage.implementation.MyPageDataStorageImpl.MyPageValue.GCN
import com.example.local_database.storage.implementation.MyPageDataStorageImpl.MyPageValue.MINUS
import com.example.local_database.storage.implementation.MyPageDataStorageImpl.MyPageValue.PHRASE
import com.example.local_database.storage.implementation.MyPageDataStorageImpl.MyPageValue.PROFILE
import com.example.local_database.storage.implementation.MyPageDataStorageImpl.MyPageValue.SCHOOL_NAME
import com.example.local_database.storage.implementation.MyPageDataStorageImpl.MyPageValue.TOTAL_POINT
import com.example.local_database.storage.implementation.MyPageDataStorageImpl.MyPageValue.USER_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@Suppress("DEPRECATION")
@SuppressLint("CommitPrefEdits")
class MyPageDataStorageImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : MyPageDataStorage {

    override suspend fun saveMyPage(myPageLocalParam: MyPageLocalParam) {
        getSharedPreference().edit()
            .putString(SCHOOL_NAME, myPageLocalParam.schoolName)
            .putString(USER_NAME, myPageLocalParam.name)
            .putString(GCN, myPageLocalParam.gcn)
            .putString(PROFILE, myPageLocalParam.profileImageUrl)
            .putInt(BONUS.toString(), myPageLocalParam.bonusPoint)
            .putInt(MINUS.toString(), myPageLocalParam.minusPoint)
            .putString(PHRASE, myPageLocalParam.phrase)
    }

    override suspend fun fetchSchoolName(): String =
        getSharedPreference().getString(SCHOOL_NAME, "")!!

    override suspend fun fetchName(): String =
        getSharedPreference().getString(USER_NAME, "")!!

    override suspend fun fetchGcn(): String =
        getSharedPreference().getString(GCN, "")!!

    override suspend fun fetchProfile(): String =
        getSharedPreference().getString(PROFILE, "")!!

    override suspend fun fetchBonusPoint(): Int =
        getSharedPreference().getInt(BONUS.toString(), 0)

    override suspend fun fetchMinusPoint(): Int =
        getSharedPreference().getInt(MINUS.toString(), 0)

    override suspend fun fetchPhrase(): String =
        getSharedPreference().getString(PHRASE, "")!!

    override suspend fun saveTotalPoint(totalPoint: Int) {
        getSharedPreference().edit()
            .putInt(TOTAL_POINT.toString(), totalPoint)
    }

    override suspend fun fetchTotalPoint(): Int =
        getSharedPreference().getInt(TOTAL_POINT.toString(), 0)

    private fun getSharedPreference() =
        PreferenceManager.getDefaultSharedPreferences(context)

    private object MyPageValue {
        const val SCHOOL_NAME = "SCHOOL_NAME"
        const val USER_NAME = "USER_NAME"
        const val GCN = "GCN"
        const val PROFILE = "PROFILE"
        const val BONUS = 0
        const val MINUS = 0
        const val PHRASE = "PHRASE"
        const val TOTAL_POINT = 0
    }
}
