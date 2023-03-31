package team.aliens.remote.service

import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import team.aliens.remote.annotation.RequiresAccessToken
import team.aliens.remote.common.HttpPath.Remains.FetchCurrentAppliedRemainsOption
import team.aliens.remote.common.HttpPath.Remains.FetchRemainsApplicationTime
import team.aliens.remote.common.HttpPath.Remains.FetchRemainsOptions
import team.aliens.remote.common.HttpPath.Remains.UpdateRemainsOption
import team.aliens.remote.common.HttpProperty.PathVariable.RemainOptionId
import team.aliens.remote.model.remains.FetchCurrentAppliedRemainsOptionResponse
import team.aliens.remote.model.remains.FetchRemainsApplicationTimeResponse
import team.aliens.remote.model.remains.FetchRemainsOptionsResponse
import java.util.*

interface RemainsService {

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
