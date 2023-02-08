package team.aliens.local_database.storage.declaration

import com.example.local_database.param.mypage.MyPageLocalParam

interface MyPageDataStorage {

    suspend fun saveMyPage(myPageLocalParam: MyPageLocalParam)

    suspend fun fetchSchoolName(): String
    suspend fun fetchName(): String
    suspend fun fetchGcn(): String
    suspend fun fetchProfile(): String
    suspend fun fetchBonusPoint(): Int
    suspend fun fetchMinusPoint(): Int
    suspend fun fetchPhrase(): String

    suspend fun saveTotalPoint(totalPoint: Int)

    suspend fun fetchTotalPoint(): Int
}
