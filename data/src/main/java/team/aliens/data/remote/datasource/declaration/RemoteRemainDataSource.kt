package team.aliens.data.remote.datasource.declaration

import team.aliens.data.remote.response.remain.FetchAvailableRemainTimeResponse
import team.aliens.data.remote.response.remain.FetchCurrentRemainOptionResponse
import team.aliens.data.remote.response.remain.FetchRemainOptionsResponse
import java.util.*

interface RemoteRemainDataSource {

    suspend fun updateRemainOption(
        remainOptionId: UUID,
    )

    suspend fun fetchCurrentRemainOption(): FetchCurrentRemainOptionResponse

    suspend fun fetchAvailableRemainTime(): FetchAvailableRemainTimeResponse

    suspend fun fetchRemainOptions(): FetchRemainOptionsResponse
}
