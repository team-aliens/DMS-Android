package team.aliens.dms.android.data.remain.repository

import team.aliens.dms.android.data.remain.model.AppliedRemainsOption
import team.aliens.dms.android.data.remain.model.RemainsApplicationTime
import team.aliens.dms.android.data.remain.model.RemainsOption
import java.util.UUID

abstract class RemainsRepository {

    abstract suspend fun updateRemainsOption(optionId: UUID): Result<Unit>

    abstract suspend fun fetchAppliedRemainsOption(): Result<AppliedRemainsOption>

    abstract suspend fun fetchRemainsApplicationTime(): Result<RemainsApplicationTime>

    abstract suspend fun fetchRemainsOptions(): Result<List<RemainsOption>>
}
