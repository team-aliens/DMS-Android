package team.aliens.dms.android.network.user.apiservice

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Query
import team.aliens.dms.android.core.jwt.RequiresAccessToken
import team.aliens.dms.android.network.user.model.EditPasswordRequest

internal abstract class UserApiService {

    @PATCH("/users/password")
    @RequiresAccessToken
    abstract suspend fun editPassword(@Body request: EditPasswordRequest)

    @GET("/users/password")
    @RequiresAccessToken
    abstract suspend fun comparePassword(@Query("password") password: String)
}
