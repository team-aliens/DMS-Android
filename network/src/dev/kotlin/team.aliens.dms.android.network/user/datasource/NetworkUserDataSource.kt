package team.aliens.dms.android.network.user.datasource

import team.aliens.dms.android.network.user.model.EditPasswordRequest

abstract class NetworkUserDataSource {

    abstract suspend fun editPassword(request: EditPasswordRequest)

    abstract suspend fun comparePassword(password: String)
}
