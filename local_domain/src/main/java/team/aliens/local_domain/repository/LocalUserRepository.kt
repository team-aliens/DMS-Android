package team.aliens.local_domain.repository

import team.aliens.local_domain.entity.notice.UserVisibleInformEntity

interface LocalUserRepository {

    suspend fun fetchUserVisible(): UserVisibleInformEntity
}