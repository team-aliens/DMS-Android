package team.aliens.remote.service

import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import team.aliens.remote.common.DormHttpPath.Remains.FetchCurrentAppliedRemainsOption
import team.aliens.remote.common.DormHttpPath.Remains.FetchRemainsApplicationTime
import team.aliens.remote.common.DormHttpPath.Remains.FetchRemainsOptions
import team.aliens.remote.common.DormHttpPath.Remains.UpdateRemainsOption
import team.aliens.remote.common.HttpProperty.PathVariable.RemainOptionId
import team.aliens.remote.model.remains.FetchCurrentAppliedRemainsOptionResponse
import team.aliens.remote.model.remains.FetchRemainsApplicationTimeResponse
import team.aliens.remote.model.remains.FetchRemainsOptionsResponse
import java.util.*

interface RemainsService {

    @PUT(UpdateRemainsOption)
    suspend fun updateRemainsOption(
        @Path(RemainOptionId) remainsOptionId: UUID,
    )

    @GET(FetchCurrentAppliedRemainsOption)
    suspend fun fetchCurrentAppliedRemainsOption(): FetchCurrentAppliedRemainsOptionResponse

    @GET(FetchRemainsApplicationTime)
    suspend fun fetchRemainsApplicationTime(): FetchRemainsApplicationTimeResponse

    @GET(FetchRemainsOptions)
    suspend fun fetchRemainsOptions(): FetchRemainsOptionsResponse
}
