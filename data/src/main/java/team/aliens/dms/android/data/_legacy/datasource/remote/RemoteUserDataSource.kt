package team.aliens.dms.android.data._legacy.datasource.remote

import team.aliens.dms.android.domain.model.user.ComparePasswordInput
import team.aliens.dms.android.domain.model.user.EditPasswordInput

interface RemoteUserDataSource {

    suspend fun editPassword(
        input: EditPasswordInput,
    )

    suspend fun comparePassword(
        input: ComparePasswordInput,
    )
}
