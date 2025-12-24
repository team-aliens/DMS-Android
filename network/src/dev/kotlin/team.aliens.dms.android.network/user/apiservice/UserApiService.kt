package team.aliens.dms.android.network.user.apiservice

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Query
import team.aliens.dms.android.core.jwt.RequiresAccessToken
import team.aliens.dms.android.network.user.model.EditPasswordRequest

internal interface UserApiService {

    @PATCH("/users/password")
    @RequiresAccessToken
    suspend fun editPassword(@Body request: EditPasswordRequest): Response<Unit>?

    @GET("/users/password")
    @RequiresAccessToken
    suspend fun comparePassword(@Query("password") password: String)
}
