package team.aliens.local_database.repository

import team.aliens.local_database.datasource.declaration.LocalUserDataSource
import team.aliens.local_database.param.toDbEntity
import team.aliens.local_domain.repository.LocalUserRepository
import javax.inject.Inject
import team.aliens.local_domain.entity.notice.UserVisibleInformEntity

class LocalUserRepositoryImpl @Inject constructor(
    private val localUserDataSource: LocalUserDataSource,
) : LocalUserRepository {

    override suspend fun fetchUserVisible(): UserVisibleInformEntity =
        localUserDataSource.fetchUserVisibleInform().toDbEntity()
}
