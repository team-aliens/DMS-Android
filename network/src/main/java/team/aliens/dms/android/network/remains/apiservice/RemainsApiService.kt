package team.aliens.dms.android.network.remains.apiservice

import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import team.aliens.dms.android.core.jwt.RequiresAccessToken
import team.aliens.dms.android.network.remains.model.FetchAppliedRemainsOptionResponse
import team.aliens.dms.android.network.remains.model.FetchRemainsApplicationTimeResponse
import team.aliens.dms.android.network.remains.model.FetchRemainsOptionsResponse
import java.util.UUID

abstract class RemainsApiService {

    @PUT("/remains/{remain-option-id}")
    @RequiresAccessToken
    abstract suspend fun updateRemainsOption(@Path("remains-option-id") remainsOptionId: UUID)

    @GET("/remains/my")
    @RequiresAccessToken
    abstract suspend fun fetchAppliedRemainsOption(): FetchAppliedRemainsOptionResponse

    @GET("/remains/available-time")
    @RequiresAccessToken
    abstract suspend fun fetchRemainsApplicationTime(): FetchRemainsApplicationTimeResponse

    @GET("/remains/options")
    @RequiresAccessToken
    abstract suspend fun fetchRemainsOptions(): FetchRemainsOptionsResponse
}
