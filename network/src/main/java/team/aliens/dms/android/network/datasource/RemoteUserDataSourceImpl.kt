package team.aliens.dms.android.network.datasource

import team.aliens.dms_android.data.datasource.remote.RemoteUserDataSource
import team.aliens.dms.android.network.apiservice.UserApiService
import team.aliens.dms.android.network.model.user.toData
import team.aliens.dms.android.network.util.sendHttpRequest
import team.aliens.dms.android.domain.model.user.ComparePasswordInput
import team.aliens.dms.android.domain.model.user.EditPasswordInput
import javax.inject.Inject

class RemoteUserDataSourceImpl @Inject constructor(
    private val userApiService: UserApiService,
) : RemoteUserDataSource {

    override suspend fun editPassword(
        input: EditPasswordInput,
    ) {
        return sendHttpRequest {
            userApiService.editPassword(
                request = input.toData()
            )
        }
    }

    override suspend fun comparePassword(
        input: ComparePasswordInput,
    ) {
        return sendHttpRequest {
            userApiService.comparePassword(
                password = input.password,
            )
        }
    }
}
