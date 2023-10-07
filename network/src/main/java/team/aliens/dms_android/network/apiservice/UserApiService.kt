package team.aliens.dms_android.network.apiservice

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Query
import team.aliens.dms_android.network.annotation.RequiresAccessToken
import team.aliens.network.common.HttpPath.User.ComparePassword
import team.aliens.network.common.HttpPath.User.EditPassword
import team.aliens.network.common.HttpProperty.QueryString.Password
import team.aliens.network.model.user.EditPasswordRequest

interface UserApiService {

    @PATCH(EditPassword)
    @team.aliens.dms_android.network.annotation.RequiresAccessToken
    suspend fun editPassword(
        @Body request: EditPasswordRequest,
    )

    @GET(ComparePassword)
    @team.aliens.dms_android.network.annotation.RequiresAccessToken
    suspend fun comparePassword(
        @Query(Password) password: String,
    )
}
