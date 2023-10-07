package team.aliens.dms_android.network.datasource

import team.aliens.data.datasource.remote.RemoteUserDataSource
import team.aliens.dms_android.network.apiservice.UserApiService
import team.aliens.dms_android.network.model.user.toData
import team.aliens.dms_android.network.util.sendHttpRequest
import team.aliens.domain.model.user.ComparePasswordInput
import team.aliens.domain.model.user.EditPasswordInput
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
