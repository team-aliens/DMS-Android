package team.aliens.domain._repository

import team.aliens.domain._model.remain.QueryCurrentAppliedRemainOptionResult
import team.aliens.domain._model.remain.QueryRemainOptionsResult
import team.aliens.domain._model.remain.QueryRemainApplicationTimeResult
import java.util.*

interface RemainRepository {

    suspend fun updateRemainOption(
        remainOptionId: UUID,
    )

    suspend fun queryCurrentAppliedRemainOption(): QueryCurrentAppliedRemainOptionResult

    suspend fun queryRemainApplicationTime(): QueryRemainApplicationTimeResult

    suspend fun queryRemainOptions(): QueryRemainOptionsResult
}
