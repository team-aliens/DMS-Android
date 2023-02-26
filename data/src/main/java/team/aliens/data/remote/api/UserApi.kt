package team.aliens.data.remote.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Query
import team.aliens.data.remote.request.user.EditPasswordRequest
import team.aliens.data.remote.url.DmsUrl

interface UserApi {

    @PATCH(DmsUrl.User.editPassword)
    suspend fun editPassword(
        @Body editPasswordRequest: EditPasswordRequest,
    )

    @GET(DmsUrl.User.comparePassword)
    suspend fun comparePassword(
        @Query("password") password: String,
    )
}