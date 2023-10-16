package team.aliens.dms.android.network._legacy.datasource

import team.aliens.dms.android.data.datasource.remote.RemoteRemainsDataSource
import team.aliens.dms.android.network._legacy.apiservice.RemainsApiService
import team.aliens.dms.android.network.model.remains.toDomain
import team.aliens.dms.android.network.util.sendHttpRequest
import team.aliens.dms.android.domain.model.remains.FetchCurrentAppliedRemainsOptionOutput
import team.aliens.dms.android.domain.model.remains.FetchRemainsApplicationTimeOutput
import team.aliens.dms.android.domain.model.remains.FetchRemainsOptionsOutput
import team.aliens.dms.android.domain.model.remains.UpdateRemainsOptionInput
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
