package team.aliens.network.datasource

import team.aliens.data.datasource.remote.RemoteRemainsDataSource
import team.aliens.domain.model.remains.FetchCurrentAppliedRemainsOptionOutput
import team.aliens.domain.model.remains.FetchRemainsApplicationTimeOutput
import team.aliens.domain.model.remains.FetchRemainsOptionsOutput
import team.aliens.domain.model.remains.UpdateRemainsOptionInput
import team.aliens.network.model.remains.toDomain
import team.aliens.network.apiservice.RemainsApiService
import team.aliens.network.util.sendHttpRequest
import javax.inject.Inject

class RemoteRemainsDataSourceImpl @Inject constructor(
    private val remainsApiService: RemainsApiService,
) : RemoteRemainsDataSource {
    override suspend fun updateRemainsOption(
        input: UpdateRemainsOptionInput,
    ) {
        sendHttpRequest {
            remainsApiService.updateRemainsOption(
                remainsOptionId = input.remainsOptionId,
            )
        }
    }

    override suspend fun fetchCurrentAppliedRemainsOption(): FetchCurrentAppliedRemainsOptionOutput {
        return sendHttpRequest {
            remainsApiService.fetchCurrentAppliedRemainsOption()
        }.toDomain()
    }

    override suspend fun fetchRemainsApplicationTime(): FetchRemainsApplicationTimeOutput {
        return sendHttpRequest {
            remainsApiService.fetchRemainsApplicationTime()
        }.toDomain()
    }

    override suspend fun fetchRemainsOptions(): FetchRemainsOptionsOutput {
        return sendHttpRequest {
            remainsApiService.fetchRemainsOptions()
        }.toDomain()
    }
}
