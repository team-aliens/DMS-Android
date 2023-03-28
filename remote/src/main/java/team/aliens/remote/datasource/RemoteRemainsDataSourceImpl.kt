package team.aliens.remote.datasource

import team.aliens.data._datasource.remote.RemoteRemainsDataSource
import team.aliens.domain._model.remains.FetchCurrentAppliedRemainsOptionOutput
import team.aliens.domain._model.remains.FetchRemainsApplicationTimeOutput
import team.aliens.domain._model.remains.FetchRemainsOptionsOutput
import java.util.*

class RemoteRemainsDataSourceImpl : RemoteRemainsDataSource {
    override suspend fun updateRemainsOption(
        remainOptionId: UUID,
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun fetchCurrentAppliedRemainsOption(): FetchCurrentAppliedRemainsOptionOutput {
        TODO("Not yet implemented")
    }

    override suspend fun fetchRemainsApplicationTime(): FetchRemainsApplicationTimeOutput {
        TODO("Not yet implemented")
    }

    override suspend fun fetchRemainsOptions(): FetchRemainsOptionsOutput {
        TODO("Not yet implemented")
    }
}
