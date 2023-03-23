package team.aliens.domain._repository

import team.aliens.domain._model.remains.FetchCurrentAppliedRemainsOptionOutput
import team.aliens.domain._model.remains.FetchRemainsApplicationTimeOutput
import team.aliens.domain._model.remains.FetchRemainsOptionsOutput
import java.util.*

interface RemainRepository {

    suspend fun updateRemainOption(
        remainOptionId: UUID,
    )

    suspend fun fetchCurrentAppliedRemainOption(): FetchCurrentAppliedRemainsOptionOutput

    suspend fun fetchRemainApplicationTime(): FetchRemainsApplicationTimeOutput

    suspend fun fetchRemainOptions(): FetchRemainsOptionsOutput
}
