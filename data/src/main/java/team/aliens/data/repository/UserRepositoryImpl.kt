package team.aliens.data.repository

import team.aliens.data.datasource.remote.RemoteUserDataSource
import team.aliens.domain._model.user.ComparePasswordInput
import team.aliens.domain._model.user.EditPasswordInput
import team.aliens.domain._repository.UserRepository
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
