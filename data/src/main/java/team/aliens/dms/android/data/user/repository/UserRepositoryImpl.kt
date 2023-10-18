package team.aliens.dms.android.data.user.repository

import team.aliens.dms.android.network.user.datasource.NetworkUserDataSource
import javax.inject.Inject

internal class UserRepositoryImpl @Inject constructor(
    private val userDataSource: NetworkUserDataSource,
) : UserRepository() {

    override suspend fun editPassword(
        currentPassword: String,
        newPassword: String,
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun comparePassword(password: String) {
        TODO("Not yet implemented")
    }
}
