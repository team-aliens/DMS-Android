package team.aliens.data.datasource.remote

import team.aliens.domain.model.user.ComparePasswordInput
import team.aliens.domain.model.user.EditPasswordInput

interface RemoteUserDataSource {

    suspend fun editPassword(
        input: EditPasswordInput,
    )

    suspend fun comparePassword(
        input: ComparePasswordInput,
    )
}
