package team.aliens.dms.android.network.remains.datasource
import team.aliens.dms.android.network.remains.apiservice.RemainsApiService
import team.aliens.dms.android.network.remains.model.FetchAppliedRemainsOptionResponse
import team.aliens.dms.android.network.remains.model.FetchRemainsApplicationTimeResponse
import team.aliens.dms.android.network.remains.model.FetchRemainsOptionsResponse
import team.aliens.dms.android.shared.exception.util.suspendRunCatching
import java.util.UUID
import javax.inject.Inject

internal class NetworkRemainsDataSourceImpl @Inject constructor(
    private val remainsApiService: RemainsApiService,
) : NetworkRemainsDataSource() {
    override suspend fun updateRemainsOption(optionId: UUID): Result<Unit> =
        suspendRunCatching { remainsApiService.updateRemainsOption(optionId) }

    override suspend fun fetchAppliedRemainsOption(): Result<FetchAppliedRemainsOptionResponse> =
        suspendRunCatching { remainsApiService.fetchAppliedRemainsOption() }

    override suspend fun fetchRemainsApplicationTime(): Result<FetchRemainsApplicationTimeResponse> =
        suspendRunCatching { remainsApiService.fetchRemainsApplicationTime() }

    override suspend fun fetchRemainsOptions(): Result<FetchRemainsOptionsResponse> =
        suspendRunCatching { remainsApiService.fetchRemainsOptions() }
}
