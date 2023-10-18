package team.aliens.dms.android.data.remains.repository

import team.aliens.dms.android.data.remains.model.FetchAppliedRemainsOptionOutput
import team.aliens.dms.android.data.remains.model.FetchRemainsApplicationTimeOutput
import team.aliens.dms.android.data.remains.model.RemainsOption
import java.util.UUID

abstract class RemainsRepository {

    abstract suspend fun updateRemainsOption(remainsOption: UUID)

    abstract suspend fun fetchAppliedRemainsOption(): FetchAppliedRemainsOptionOutput

    abstract suspend fun fetchRemainsApplicationTime(): FetchRemainsApplicationTimeOutput

    abstract suspend fun fetchRemainsOptions(): List<RemainsOption>
}
