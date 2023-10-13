package team.aliens.dms.android.network.apiservice

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Query
import team.aliens.dms.android.network.annotation.RequiresAccessToken
import team.aliens.dms.android.network.common.HttpPath.User.ComparePassword
import team.aliens.dms.android.network.common.HttpPath.User.EditPassword
import team.aliens.dms.android.network.common.HttpProperty.QueryString.Password
import team.aliens.dms.android.network.model.user.EditPasswordRequest

interface UserApiService {

    @PATCH(EditPassword)
    @RequiresAccessToken
    suspend fun editPassword(
        @Body request: EditPasswordRequest,
    )

    @GET(ComparePassword)
    @RequiresAccessToken
    suspend fun comparePassword(
        @Query(Password) password: String,
    )
}
