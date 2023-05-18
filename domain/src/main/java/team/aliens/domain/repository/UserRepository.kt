package team.aliens.domain.repository

import team.aliens.domain.model.user.ComparePasswordInput
import team.aliens.domain.model.user.EditPasswordInput

interface UserRepository {

    suspend fun editPassword(
        input: EditPasswordInput,
    )

    suspend fun comparePassword(
        input: ComparePasswordInput,
    )
}
