package team.aliens.data.remote.api

import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import team.aliens.data.remote.response.remain.FetchAvailableRemainTimeResponse
import team.aliens.data.remote.response.remain.FetchCurrentRemainOptionResponse
import team.aliens.data.remote.response.remain.FetchRemainOptionsResponse
import team.aliens.data.remote.url.DmsUrl
import java.util.*

interface RemainApi {

    @PUT(DmsUrl.Remain.updateRemainOption)
    suspend fun updateRemainOption(
        @Path("remain-option-id") remainOptionId: UUID,
    )

    @GET(DmsUrl.Remain.fetchCurrentRemainOption)
    suspend fun fetchCurrentRemainOption(): FetchCurrentRemainOptionResponse

    @GET(DmsUrl.Remain.fetchAvailableRemainTime)
    suspend fun fetchAvailableRemainTime(): FetchAvailableRemainTimeResponse

    @GET(DmsUrl.Remain.fetchRemainOptions)
    suspend fun fetchRemainOptions(): FetchRemainOptionsResponse
}
