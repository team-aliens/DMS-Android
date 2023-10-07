package team.aliens.dms_android.network.apiservice

import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import team.aliens.dms_android.network.annotation.RequiresAccessToken
import team.aliens.dms_android.network.common.HttpPath.Remains.FetchCurrentAppliedRemainsOption
import team.aliens.dms_android.network.common.HttpPath.Remains.FetchRemainsApplicationTime
import team.aliens.dms_android.network.common.HttpPath.Remains.FetchRemainsOptions
import team.aliens.dms_android.network.common.HttpPath.Remains.UpdateRemainsOption
import team.aliens.dms_android.network.common.HttpProperty.PathVariable.RemainOptionId
import team.aliens.dms_android.network.model.remains.FetchCurrentAppliedRemainsOptionResponse
import team.aliens.dms_android.network.model.remains.FetchRemainsApplicationTimeResponse
import team.aliens.dms_android.network.model.remains.FetchRemainsOptionsResponse
import java.util.*

interface RemainsApiService {

    @PUT(UpdateRemainsOption)
    @RequiresAccessToken
    suspend fun updateRemainsOption(
        @Path(RemainOptionId) remainsOptionId: UUID,
    )

    @GET(FetchCurrentAppliedRemainsOption)
    @RequiresAccessToken
    suspend fun fetchCurrentAppliedRemainsOption(): FetchCurrentAppliedRemainsOptionResponse

    @GET(FetchRemainsApplicationTime)
    @RequiresAccessToken
    suspend fun fetchRemainsApplicationTime(): FetchRemainsApplicationTimeResponse

    @GET(FetchRemainsOptions)
    @RequiresAccessToken
    suspend fun fetchRemainsOptions(): FetchRemainsOptionsResponse
}
