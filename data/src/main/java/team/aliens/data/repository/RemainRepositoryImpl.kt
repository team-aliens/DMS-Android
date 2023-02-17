package team.aliens.data.repository

import team.aliens.data.remote.datasource.declaration.RemoteRemainDataSource
import team.aliens.data.remote.response.remain.toEntity
import team.aliens.domain.entity.remain.AvailableRemainTimeEntity
import team.aliens.domain.entity.remain.CurrentRemainOptionEntity
import team.aliens.domain.entity.remain.RemainOptionsEntity
import team.aliens.domain.repository.RemainRepository
import java.util.*
import javax.inject.Inject

class RemainRepositoryImpl @Inject constructor(
    private val remoteRemainDataSource: RemoteRemainDataSource,
) : RemainRepository {

    override suspend fun updateRemainOption(remainOptionId: UUID) {
        remoteRemainDataSource.updateRemainOption(
            remainOptionId = remainOptionId,
        )
    }

    override suspend fun fetchCurrentRemainOption(): CurrentRemainOptionEntity {
        return remoteRemainDataSource.fetchCurrentRemainOption().toEntity()
    }

    override suspend fun fetchAvailableRemainTime(): AvailableRemainTimeEntity {
        return remoteRemainDataSource.fetchAvailableRemainTime().toEntity()
    }

    override suspend fun fetchRemainOptions(): RemainOptionsEntity {
        return remoteRemainDataSource.fetchRemainOptions().toEntity()
    }
}
