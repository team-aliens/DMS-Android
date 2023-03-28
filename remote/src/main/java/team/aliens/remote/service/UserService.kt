package team.aliens.remote.service

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Query
import team.aliens.remote.common.DormHttpPath.Users.ComparePassword
import team.aliens.remote.common.DormHttpPath.Users.EditPassword
import team.aliens.remote.common.HttpProperty.QueryString.Password
import team.aliens.remote.model.user.EditPasswordRequest

interface UserService {

    @PATCH(EditPassword)
    suspend fun editPassword(
        @Body request: EditPasswordRequest,
    )

    @GET(ComparePassword)
    suspend fun comparePassword(
        @Query(Password) password: String,
    )
}
