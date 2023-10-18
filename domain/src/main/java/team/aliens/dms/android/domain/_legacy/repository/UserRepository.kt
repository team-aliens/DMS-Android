package team.aliens.dms.android.domain._legacy.repository

import team.aliens.dms.android.domain.model.user.ComparePasswordInput
import team.aliens.dms.android.domain.model.user.EditPasswordInput

interface UserRepository {

    suspend fun editPassword(
        input: EditPasswordInput,
    )

    suspend fun comparePassword(
        input: ComparePasswordInput,
    )
}
