package team.aliens.domain._repository

import team.aliens.domain._model.user.EditPasswordInput

interface UserRepository {

    suspend fun editPassword(
        input: EditPasswordInput,
    )

    suspend fun comparePassword(
        password: String,
    )
}
