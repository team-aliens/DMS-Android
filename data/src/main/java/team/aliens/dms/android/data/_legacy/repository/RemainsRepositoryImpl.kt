package team.aliens.dms.android.data._legacy.repository

import team.aliens.dms.android.data.datasource.remote.RemoteRemainsDataSource
import team.aliens.dms.android.domain.model.remains.FetchCurrentAppliedRemainsOptionOutput
import team.aliens.dms.android.domain.model.remains.FetchRemainsApplicationTimeOutput
import team.aliens.dms.android.domain.model.remains.FetchRemainsOptionsOutput
import team.aliens.dms.android.domain.model.remains.UpdateRemainsOptionInput
import team.aliens.dms.android.domain.repository.RemainsRepository
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