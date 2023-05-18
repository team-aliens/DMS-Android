package team.aliens.remote.datasource

import team.aliens.data.datasource.remote.RemoteRemainsDataSource
import team.aliens.domain._model.remains.FetchCurrentAppliedRemainsOptionOutput
import team.aliens.domain._model.remains.FetchRemainsApplicationTimeOutput
import team.aliens.domain._model.remains.FetchRemainsOptionsOutput
import team.aliens.domain._model.remains.UpdateRemainsOptionInput
import team.aliens.remote.model.remains.toDomain
import team.aliens.remote.apiservice.RemainsApiService
import team.aliens.remote.util.sendHttpRequest
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
