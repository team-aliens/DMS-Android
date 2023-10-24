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

    override suspend fun updateRemainsOption(remainsOption: UUID) {
        TODO("Not yet implemented")
    }

    override suspend fun fetchAppliedRemainsOption(): AppliedRemainsOption =
        networkRemainsDataSource.fetchAppliedRemainsOption().toModel()

    override suspend fun fetchRemainsApplicationTime(): RemainsApplicationTime {
        TODO("Not yet implemented")
    }

    override suspend fun fetchRemainsOptions(): List<RemainsOption> {
        TODO("Not yet implemented")
    }
}
