package team.aliens.remote.datasource

import team.aliens.data._datasource.remote.RemoteUserDataSource
import team.aliens.domain._model.user.EditPasswordInput

class RemoteUserDataSourceImpl : RemoteUserDataSource {

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
