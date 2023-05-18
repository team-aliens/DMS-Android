package team.aliens.data.datasource.remote

import team.aliens.domain._model.user.ComparePasswordInput
import team.aliens.domain._model.user.EditPasswordInput

interface RemoteUserDataSource {

    suspend fun editPassword(
        input: EditPasswordInput,
    )

    suspend fun comparePassword(
        input: ComparePasswordInput,
    )
}
