package team.aliens.dms.android.network.remains.apiservice

import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import team.aliens.dms.android.core.jwt.RequiresAccessToken
import team.aliens.dms.android.network.remains.model.FetchAppliedRemainsOptionResponse
import team.aliens.dms.android.network.remains.model.FetchRemainsApplicationTimeResponse
import team.aliens.dms.android.network.remains.model.FetchRemainsOptionsResponse
import java.util.UUID

internal interface RemainsApiService {

    @PUT("/remains/{remains-option-id}")
    @RequiresAccessToken
    suspend fun updateRemainsOption(@Path("remains-option-id") optionId: UUID)

    @GET("/remains/my")
    @RequiresAccessToken
    suspend fun fetchAppliedRemainsOption(): FetchAppliedRemainsOptionResponse

    @GET("/remains/available-time")
    @RequiresAccessToken
    suspend fun fetchRemainsApplicationTime(): FetchRemainsApplicationTimeResponse

    @GET("/remains/options")
    @RequiresAccessToken
    suspend fun fetchRemainsOptions(): FetchRemainsOptionsResponse
}
