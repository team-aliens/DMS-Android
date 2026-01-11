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

    override suspend fun updateRemainsOption(optionId: UUID): Result<Unit> =
        networkRemainsDataSource.updateRemainsOption(optionId)

    override suspend fun fetchAppliedRemainsOption(): Result<AppliedRemainsOption> =
        networkRemainsDataSource.fetchAppliedRemainsOption()
            .map { it.toModel() }

    override suspend fun fetchRemainsApplicationTime(): Result<RemainsApplicationTime> =
        networkRemainsDataSource.fetchRemainsApplicationTime()
            .map { it.toModel() }

    override suspend fun fetchRemainsOptions(): Result<List<RemainsOption>> =
        networkRemainsDataSource.fetchRemainsOptions()
            .map { it.toModel() }
}
