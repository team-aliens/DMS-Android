package team.aliens.remote.datasource

import team.aliens.data._datasource.remote.RemoteUserDataSource
import team.aliens.domain._model.user.ComparePasswordInput
import team.aliens.domain._model.user.EditPasswordInput
import team.aliens.remote.model.user.toData
import team.aliens.remote.service.UserService
import team.aliens.remote.util.sendHttpRequest
import javax.inject.Inject

class RemoteUserDataSourceImpl @Inject constructor(
    private val userService: UserService,
) : RemoteUserDataSource {

    override suspend fun editPassword(
        input: EditPasswordInput,
    ) {
        return sendHttpRequest {
            userService.editPassword(
                request = input.toData()
            )
        }
    }

    override suspend fun comparePassword(
        input: ComparePasswordInput,
    ) {
        return sendHttpRequest {
            userService.comparePassword(
                password = input.password,
            )
        }
    }
}
