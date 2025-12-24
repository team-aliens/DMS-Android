package team.aliens.dms.android.data.remains.repository

import team.aliens.dms.android.data.remains.mapper.toModel
import team.aliens.dms.android.data.remains.model.AppliedRemainsOption
import team.aliens.dms.android.data.remains.model.RemainsApplicationTime
import team.aliens.dms.android.data.remains.model.RemainsOption
import team.aliens.dms.android.network.remains.datasource.NetworkRemainsDataSource
import java.util.UUID
import javax.inject.Inject

internal class RemainsRepositoryImpl @Inject constructor(
    private val networkRemainsDataSource: NetworkRemainsDataSource,
) : RemainsRepository() {

    override suspend fun updateRemainsOption(optionId: UUID) {
        networkRemainsDataSource.updateRemainsOption(optionId)
    }

    override suspend fun fetchAppliedRemainsOption(): AppliedRemainsOption =
        networkRemainsDataSource.fetchAppliedRemainsOption().toModel()

    override suspend fun fetchRemainsApplicationTime(): RemainsApplicationTime =
        networkRemainsDataSource.fetchRemainsApplicationTime().toModel()

    override suspend fun fetchRemainsOptions(): List<RemainsOption> =
        networkRemainsDataSource.fetchRemainsOptions().toModel()
}
