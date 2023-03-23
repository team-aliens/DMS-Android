package team.aliens.data._datasource.remote

import team.aliens.domain._model.user.EditPasswordInput

interface RemoteUserDataSource {

    suspend fun editPassword(
        input: EditPasswordInput,
    )

    suspend fun comparePassword(
        password: String,
    )
}
