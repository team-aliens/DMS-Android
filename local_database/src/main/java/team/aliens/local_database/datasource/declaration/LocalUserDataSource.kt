package team.aliens.local_database.datasource.declaration

import team.aliens.local_database.param.UserVisibleParam
import team.aliens.local_database.param.UserPersonalKeyParam

interface LocalUserDataSource {

    suspend fun setPersonalKey(personalKeyParam: UserPersonalKeyParam)
    suspend fun fetchPersonalKey(): UserPersonalKeyParam

    suspend fun setUserVisibleInform(userVisibleParam: UserVisibleParam)
    suspend fun fetchUserVisibleInform(): UserVisibleParam

    suspend fun setAutoSignInOption(autoSignInEnabled: Boolean)
    suspend fun fetchAutoSignInOption(): Boolean

    suspend fun signOut()
}
