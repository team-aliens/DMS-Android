package team.aliens.data._repository

import team.aliens.domain._model.user.EditPasswordInput
import team.aliens.domain._repository.UserRepository

class UserRepositoryImpl(
    // private val remoteUserDataSource: RemoteUserDataSource,
) : UserRepository {

    override suspend fun editPassword(
        input: EditPasswordInput,
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun comparePassword(
        password: String,
    ) {
        TODO("Not yet implemented")
    }
}
