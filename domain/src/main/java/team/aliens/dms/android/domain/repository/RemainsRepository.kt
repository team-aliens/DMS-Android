package team.aliens.dms.android.domain.repository

import team.aliens.dms.android.domain.model.remains.FetchCurrentAppliedRemainsOptionOutput
import team.aliens.dms.android.domain.model.remains.FetchRemainsApplicationTimeOutput
import team.aliens.dms.android.domain.model.remains.FetchRemainsOptionsOutput
import team.aliens.dms.android.domain.model.remains.UpdateRemainsOptionInput

interface RemainsRepository {

    suspend fun updateRemainsOption(
        input: UpdateRemainsOptionInput,
    )

    suspend fun fetchCurrentAppliedRemainsOption(): FetchCurrentAppliedRemainsOptionOutput

    suspend fun fetchRemainsApplicationTime(): FetchRemainsApplicationTimeOutput

    suspend fun fetchRemainsOptions(): FetchRemainsOptionsOutput
}
