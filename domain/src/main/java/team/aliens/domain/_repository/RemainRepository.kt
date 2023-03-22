package team.aliens.domain._repository

import team.aliens.domain._model.remains.QueryCurrentAppliedRemainsOptionResult
import team.aliens.domain._model.remains.QueryRemainsOptionsResult
import team.aliens.domain._model.remains.QueryRemainsApplicationTimeResult
import java.util.*

interface RemainRepository {

    suspend fun updateRemainOption(
        remainOptionId: UUID,
    )

    suspend fun queryCurrentAppliedRemainOption(): QueryCurrentAppliedRemainsOptionResult

    suspend fun queryRemainApplicationTime(): QueryRemainsApplicationTimeResult

    suspend fun queryRemainOptions(): QueryRemainsOptionsResult
}
