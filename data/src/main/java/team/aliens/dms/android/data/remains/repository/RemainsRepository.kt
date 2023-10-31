package team.aliens.dms.android.data.remains.repository

import team.aliens.dms.android.data.remains.model.AppliedRemainsOption
import team.aliens.dms.android.data.remains.model.RemainsApplicationTime
import team.aliens.dms.android.data.remains.model.RemainsOption
import java.util.UUID

abstract class RemainsRepository {

    abstract suspend fun updateRemainsOption(optionId: UUID)

    abstract suspend fun fetchAppliedRemainsOption(): AppliedRemainsOption

    abstract suspend fun fetchRemainsApplicationTime(): RemainsApplicationTime

    abstract suspend fun fetchRemainsOptions(): List<RemainsOption>
}
