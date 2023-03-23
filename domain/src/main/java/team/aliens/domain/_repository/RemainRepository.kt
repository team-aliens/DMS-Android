package team.aliens.domain._repository

import team.aliens.domain._model.remains.FetchCurrentAppliedRemainsOptionOutput
import team.aliens.domain._model.remains.FetchRemainsOptionsOutput
import team.aliens.domain._model.remains.FetchRemainsApplicationTimeOutput
import java.util.*

interface RemainRepository {

    suspend fun updateRemainOption(
        remainOptionId: UUID,
    )

    suspend fun queryCurrentAppliedRemainOption(): FetchCurrentAppliedRemainsOptionOutput

    suspend fun queryRemainApplicationTime(): FetchRemainsApplicationTimeOutput

    suspend fun queryRemainOptions(): FetchRemainsOptionsOutput
}
