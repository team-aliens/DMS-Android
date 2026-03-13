package team.aliens.dms.android.network.remains.datasource

import team.aliens.dms.android.network.remains.model.FetchAppliedRemainsOptionResponse
import team.aliens.dms.android.network.remains.model.FetchRemainsApplicationTimeResponse
import team.aliens.dms.android.network.remains.model.FetchRemainsOptionsResponse
import java.util.UUID

abstract class NetworkRemainsDataSource {

    abstract suspend fun updateRemainsOption(optionId: UUID): Result<Unit>

    abstract suspend fun fetchAppliedRemainsOption(): Result<FetchAppliedRemainsOptionResponse>

    abstract suspend fun fetchRemainsApplicationTime(): Result<FetchRemainsApplicationTimeResponse>

    abstract suspend fun fetchRemainsOptions(): Result<FetchRemainsOptionsResponse>
}
