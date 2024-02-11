package team.aliens.dms.android.data.user.repository

import team.aliens.dms.android.network.user.datasource.NetworkUserDataSource
import team.aliens.dms.android.network.user.model.EditPasswordRequest
import javax.inject.Inject

internal class UserRepositoryImpl @Inject constructor(
    private val networkUserDataSource: NetworkUserDataSource,
) : UserRepository() {

    override suspend fun editPassword(
        currentPassword: String,
        newPassword: String,
    ) {
        networkUserDataSource.editPassword(
            request = EditPasswordRequest(
                currentPassword = currentPassword,
                newPassword = newPassword,
            ),
        )
    }

    override suspend fun comparePassword(password: String) {
        networkUserDataSource.comparePassword(password = password)
    }
}
