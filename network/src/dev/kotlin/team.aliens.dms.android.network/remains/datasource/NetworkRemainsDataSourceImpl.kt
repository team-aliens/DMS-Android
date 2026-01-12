package team.aliens.dms.android.network.remains.datasource
import team.aliens.dms.android.network.remains.apiservice.RemainsApiService
import team.aliens.dms.android.network.remains.model.FetchAppliedRemainsOptionResponse
import team.aliens.dms.android.network.remains.model.FetchRemainsApplicationTimeResponse
import team.aliens.dms.android.network.remains.model.FetchRemainsOptionsResponse
import java.util.UUID
import javax.inject.Inject

internal class NetworkRemainsDataSourceImpl @Inject constructor(
    private val remainsApiService: RemainsApiService,
) : NetworkRemainsDataSource() {
    override suspend fun updateRemainsOption(optionId: UUID): Result<Unit> =
        runCatching { remainsApiService.updateRemainsOption(optionId) }

    override suspend fun fetchAppliedRemainsOption(): Result<FetchAppliedRemainsOptionResponse> =
        runCatching { remainsApiService.fetchAppliedRemainsOption() }

    override suspend fun fetchRemainsApplicationTime(): Result<FetchRemainsApplicationTimeResponse> =
        runCatching { remainsApiService.fetchRemainsApplicationTime() }

    override suspend fun fetchRemainsOptions(): Result<FetchRemainsOptionsResponse> =
        runCatching { remainsApiService.fetchRemainsOptions() }
}
