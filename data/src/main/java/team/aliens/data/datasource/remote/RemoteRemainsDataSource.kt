package team.aliens.data.datasource.remote

import team.aliens.domain.model.remains.FetchCurrentAppliedRemainsOptionOutput
import team.aliens.domain.model.remains.FetchRemainsApplicationTimeOutput
import team.aliens.domain.model.remains.FetchRemainsOptionsOutput
import team.aliens.domain.model.remains.UpdateRemainsOptionInput

interface RemoteRemainsDataSource {

    suspend fun updateRemainsOption(
        input: UpdateRemainsOptionInput,
    )

    suspend fun fetchCurrentAppliedRemainsOption(): FetchCurrentAppliedRemainsOptionOutput

    suspend fun fetchRemainsApplicationTime(): FetchRemainsApplicationTimeOutput

    suspend fun fetchRemainsOptions(): FetchRemainsOptionsOutput
}
