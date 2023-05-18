package team.aliens.remote.datasource

import team.aliens.data.datasource.remote.RemoteUserDataSource
import team.aliens.domain.model.user.ComparePasswordInput
import team.aliens.domain.model.user.EditPasswordInput
import team.aliens.remote.model.user.toData
import team.aliens.remote.apiservice.UserApiService
import team.aliens.remote.util.sendHttpRequest
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
