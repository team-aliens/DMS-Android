package team.aliens.dms.android.network.user.datasource

import team.aliens.dms.android.core.network.util.sendHttpRequest
import team.aliens.dms.android.network.user.apiservice.UserApiService
import team.aliens.dms.android.network.user.model.EditPasswordRequest
import javax.inject.Inject

internal class NetworkUserDataSourceImpl @Inject constructor(
    private val userApiService: UserApiService,
) : NetworkUserDataSource() {
    override suspend fun editPassword(request: EditPasswordRequest) =
        sendHttpRequest { userApiService.editPassword(request) }

    override suspend fun comparePassword(password: String) =
        sendHttpRequest { userApiService.comparePassword(password) }
}
