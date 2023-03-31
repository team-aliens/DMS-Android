package team.aliens.remote.datasource

import team.aliens.data._datasource.remote.RemoteRemainsDataSource
import team.aliens.domain._model.remains.FetchCurrentAppliedRemainsOptionOutput
import team.aliens.domain._model.remains.FetchRemainsApplicationTimeOutput
import team.aliens.domain._model.remains.FetchRemainsOptionsOutput
import team.aliens.remote.model.remains.toDomain
import team.aliens.remote.service.RemainsService
import team.aliens.remote.util.sendHttpRequest
import java.util.*
import javax.inject.Inject

class RemoteRemainsDataSourceImpl @Inject constructor(
    private val remainsService: RemainsService,
) : RemoteRemainsDataSource {
    override suspend fun updateRemainsOption(
        remainsOptionId: UUID,
    ) {
        sendHttpRequest {
            remainsService.updateRemainsOption(
                remainsOptionId = remainsOptionId,
            )
        }
    }

    override suspend fun fetchCurrentAppliedRemainsOption(): FetchCurrentAppliedRemainsOptionOutput {
        return sendHttpRequest {
            remainsService.fetchCurrentAppliedRemainsOption()
        }.toDomain()
    }

    override suspend fun fetchRemainsApplicationTime(): FetchRemainsApplicationTimeOutput {
        return sendHttpRequest {
            remainsService.fetchRemainsApplicationTime()
        }.toDomain()
    }

    override suspend fun fetchRemainsOptions(): FetchRemainsOptionsOutput {
        return sendHttpRequest {
            remainsService.fetchRemainsOptions()
        }.toDomain()
    }
}
