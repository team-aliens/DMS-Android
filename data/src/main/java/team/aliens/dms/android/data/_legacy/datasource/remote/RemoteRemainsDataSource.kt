package team.aliens.dms.android.data._legacy.datasource.remote

import team.aliens.dms.android.domain.model.remains.FetchCurrentAppliedRemainsOptionOutput
import team.aliens.dms.android.domain.model.remains.FetchRemainsApplicationTimeOutput
import team.aliens.dms.android.domain.model.remains.FetchRemainsOptionsOutput
import team.aliens.dms.android.domain.model.remains.UpdateRemainsOptionInput

interface RemoteRemainsDataSource {

    suspend fun updateRemainsOption(
        input: UpdateRemainsOptionInput,
    )

    suspend fun fetchCurrentAppliedRemainsOption(): FetchCurrentAppliedRemainsOptionOutput

    suspend fun fetchRemainsApplicationTime(): FetchRemainsApplicationTimeOutput

    suspend fun fetchRemainsOptions(): FetchRemainsOptionsOutput
}