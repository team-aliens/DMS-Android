package team.aliens.data._repository

import team.aliens.domain._model.remains.FetchCurrentAppliedRemainsOptionOutput
import team.aliens.domain._model.remains.FetchRemainsApplicationTimeOutput
import team.aliens.domain._model.remains.FetchRemainsOptionsOutput
import team.aliens.domain._repository.RemainsRepository
import java.util.*

class RemainsRepositoryImpl(
    // private val remoteRemainsDataSource: RemoteRemainsDataSource,
) : RemainsRepository {

    override suspend fun updateRemainsOption(
        remainsOptionId: UUID,
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