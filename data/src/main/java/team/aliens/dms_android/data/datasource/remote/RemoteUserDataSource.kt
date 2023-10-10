package team.aliens.dms_android.data.datasource.remote

import team.aliens.dms_android.domain.model.user.ComparePasswordInput
import team.aliens.dms_android.domain.model.user.EditPasswordInput

interface RemoteUserDataSource {

    suspend fun editPassword(
        input: EditPasswordInput,
    )

    suspend fun comparePassword(
        input: ComparePasswordInput,
    )
}
