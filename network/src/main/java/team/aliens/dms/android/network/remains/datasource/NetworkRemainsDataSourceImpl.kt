package team.aliens.dms.android.network.remains.datasource

import team.aliens.dms.android.core.network.util.sendHttpRequest
import team.aliens.dms.android.network.remains.apiservice.RemainsApiService
import team.aliens.dms.android.network.remains.model.FetchAppliedRemainsOptionResponse
import team.aliens.dms.android.network.remains.model.FetchRemainsApplicationTimeResponse
import team.aliens.dms.android.network.remains.model.FetchRemainsOptionsResponse
import java.util.UUID
import javax.inject.Inject

internal class NetworkRemainsDataSourceImpl @Inject constructor(
    private val remainsApiService: RemainsApiService,
) : NetworkRemainsDataSource() {
    override suspend fun updateRemainsOption(optionId: UUID) =
        sendHttpRequest { remainsApiService.updateRemainsOption(optionId) }

    override suspend fun fetchAppliedRemainsOption(): FetchAppliedRemainsOptionResponse =
        sendHttpRequest { remainsApiService.fetchAppliedRemainsOption() }

    override suspend fun fetchRemainsApplicationTime(): FetchRemainsApplicationTimeResponse =
        sendHttpRequest { remainsApiService.fetchRemainsApplicationTime() }

    override suspend fun fetchRemainsOptions(): FetchRemainsOptionsResponse =
        sendHttpRequest { remainsApiService.fetchRemainsOptions() }
}
