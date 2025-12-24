package team.aliens.dms.android.network.remains.datasource

import team.aliens.dms.android.network.remains.model.FetchAppliedRemainsOptionResponse
import team.aliens.dms.android.network.remains.model.FetchRemainsApplicationTimeResponse
import team.aliens.dms.android.network.remains.model.FetchRemainsOptionsResponse
import java.util.UUID

abstract class NetworkRemainsDataSource {

    abstract suspend fun updateRemainsOption(optionId: UUID)

    abstract suspend fun fetchAppliedRemainsOption(): FetchAppliedRemainsOptionResponse

    abstract suspend fun fetchRemainsApplicationTime(): FetchRemainsApplicationTimeResponse

    abstract suspend fun fetchRemainsOptions(): FetchRemainsOptionsResponse
}
