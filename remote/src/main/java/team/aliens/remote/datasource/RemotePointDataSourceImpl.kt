package team.aliens.remote.datasource

import team.aliens.data._datasource.remote.RemotePointDataSource
import team.aliens.domain._model.point.FetchPointsOutput

class RemotePointDataSourceImpl : RemotePointDataSource {
    override suspend fun fetchPoints(): FetchPointsOutput {
        TODO("Not yet implemented")
    }
}
