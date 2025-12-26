package team.aliens.dms.android.data.remain.repository

import team.aliens.dms.android.data.remain.mapper.toModel
import team.aliens.dms.android.data.remain.model.AppliedRemainsOption
import team.aliens.dms.android.data.remain.model.RemainsApplicationTime
import team.aliens.dms.android.data.remain.model.RemainsOption
import team.aliens.dms.android.network.remains.datasource.NetworkRemainsDataSource
import java.util.UUID
import javax.inject.Inject

internal class RemainsRepositoryImpl @Inject constructor(
    private val networkRemainsDataSource: NetworkRemainsDataSource,
) : RemainsRepository() {

    override suspend fun updateRemainsOption(optionId: UUID): Result<Unit> = runCatching {
        networkRemainsDataSource.updateRemainsOption(optionId)
    }

    override suspend fun fetchAppliedRemainsOption(): Result<AppliedRemainsOption> = runCatching {
        networkRemainsDataSource.fetchAppliedRemainsOption().toModel()
    }

    override suspend fun fetchRemainsApplicationTime(): Result<RemainsApplicationTime> = runCatching {
        networkRemainsDataSource.fetchRemainsApplicationTime().toModel()
    }

    override suspend fun fetchRemainsOptions(): Result<List<RemainsOption>> = runCatching {
        networkRemainsDataSource.fetchRemainsOptions().toModel()
    }
}
