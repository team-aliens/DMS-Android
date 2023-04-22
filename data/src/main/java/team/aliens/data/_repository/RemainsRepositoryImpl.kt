package team.aliens.data._repository

import team.aliens.data._datasource.remote.RemoteRemainsDataSource
import team.aliens.domain._model.remains.FetchCurrentAppliedRemainsOptionOutput
import team.aliens.domain._model.remains.FetchRemainsApplicationTimeOutput
import team.aliens.domain._model.remains.FetchRemainsOptionsOutput
import team.aliens.domain._model.remains.UpdateRemainsOptionInput
import team.aliens.domain._repository.RemainsRepository
import java.util.*
import javax.inject.Inject

class RemainsRepositoryImpl @Inject constructor(
    private val remoteRemainsDataSource: RemoteRemainsDataSource,
) : RemainsRepository {

    override suspend fun updateRemainsOption(
        input: UpdateRemainsOptionInput,
    ) {
        return remoteRemainsDataSource.updateRemainsOption(
            input = input,
        )
    }

    override suspend fun fetchCurrentAppliedRemainsOption(): FetchCurrentAppliedRemainsOptionOutput {
        return remoteRemainsDataSource.fetchCurrentAppliedRemainsOption()
    }

    override suspend fun fetchRemainsApplicationTime(): FetchRemainsApplicationTimeOutput {
        return remoteRemainsDataSource.fetchRemainsApplicationTime()
    }

    override suspend fun fetchRemainsOptions(): FetchRemainsOptionsOutput {
        return remoteRemainsDataSource.fetchRemainsOptions()
    }
}