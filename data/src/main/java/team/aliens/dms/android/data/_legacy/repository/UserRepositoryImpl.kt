package team.aliens.dms.android.data._legacy.repository

import team.aliens.dms.android.data.datasource.remote.RemoteUserDataSource
import team.aliens.dms.android.domain.model.user.ComparePasswordInput
import team.aliens.dms.android.domain.model.user.EditPasswordInput
import team.aliens.dms.android.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteUserDataSource: RemoteUserDataSource,
) : UserRepository {

    override suspend fun editPassword(
        input: EditPasswordInput,
    ) {
        return remoteUserDataSource.editPassword(
            input = input,
        )
    }

    override suspend fun comparePassword(
        input: ComparePasswordInput,
    ) {
        return remoteUserDataSource.comparePassword(
            input = input,
        )
    }
}
