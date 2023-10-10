package team.aliens.dms_android.domain.repository

import team.aliens.dms_android.domain.model.user.ComparePasswordInput
import team.aliens.dms_android.domain.model.user.EditPasswordInput

interface UserRepository {

    suspend fun editPassword(
        input: EditPasswordInput,
    )

    suspend fun comparePassword(
        input: ComparePasswordInput,
    )
}
