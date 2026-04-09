package team.aliens.dms.android.network.user.datasource

import team.aliens.dms.android.network.user.apiservice.UserApiService
import team.aliens.dms.android.network.user.model.EditPasswordRequest
import team.aliens.dms.android.shared.exception.util.runCatchingCancellable
import javax.inject.Inject

internal class NetworkUserDataSourceImpl @Inject constructor(
    private val userApiService: UserApiService,
) : NetworkUserDataSource() {
    override suspend fun editPassword(request: EditPasswordRequest): Result<Unit> =
        runCatchingCancellable {
            userApiService.editPassword(request)
        }

    override suspend fun comparePassword(password: String): Result<Unit> =
        runCatchingCancellable {
            userApiService.comparePassword(password)
        }
}
