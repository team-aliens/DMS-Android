package team.aliens.data.remote.datasource.implementation

import team.aliens.data.remote.api.RemainApi
import team.aliens.data.remote.datasource.declaration.RemoteRemainDataSource
import team.aliens.data.remote.response.remain.FetchAvailableRemainTimeResponse
import team.aliens.data.remote.response.remain.FetchCurrentRemainOptionResponse
import team.aliens.data.remote.response.remain.FetchRemainOptionsResponse
import team.aliens.data.util.sendHttpRequest
import java.util.*
import javax.inject.Inject

class RemoteRemainDataSourceImpl @Inject constructor(
    private val remainApi: RemainApi,
) : RemoteRemainDataSource {

    override suspend fun updateRemainOption(remainOptionId: UUID) {
        sendHttpRequest(
            httpRequest = {
                remainApi.updateRemainOption(
                    remainOptionId = remainOptionId,
                )
            },
        )
    }

    override suspend fun fetchCurrentRemainOption(): FetchCurrentRemainOptionResponse {
        return sendHttpRequest(
            httpRequest = {
                remainApi.fetchCurrentRemainOption()
            },
        )
    }

    override suspend fun fetchAvailableRemainTime(): FetchAvailableRemainTimeResponse {
        return sendHttpRequest(
            httpRequest = {
                remainApi.fetchAvailableRemainTime()
            },
        )
    }

    override suspend fun fetchRemainOptions(): FetchRemainOptionsResponse {
        return sendHttpRequest(
            httpRequest = {
                remainApi.fetchRemainOptions()
            },
        )
    }
}
