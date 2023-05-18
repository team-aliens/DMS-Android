package team.aliens.domain.repository

import team.aliens.domain.model.remains.FetchCurrentAppliedRemainsOptionOutput
import team.aliens.domain.model.remains.FetchRemainsApplicationTimeOutput
import team.aliens.domain.model.remains.FetchRemainsOptionsOutput
import team.aliens.domain.model.remains.UpdateRemainsOptionInput

interface RemainsRepository {

    suspend fun updateRemainsOption(
        input: UpdateRemainsOptionInput,
    )

    suspend fun fetchCurrentAppliedRemainsOption(): FetchCurrentAppliedRemainsOptionOutput

    suspend fun fetchRemainsApplicationTime(): FetchRemainsApplicationTimeOutput

    suspend fun fetchRemainsOptions(): FetchRemainsOptionsOutput
}
