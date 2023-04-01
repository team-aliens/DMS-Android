package team.aliens.local_domain.usecase.uservisible

import javax.inject.Inject
import team.aliens.local_domain.entity.notice.UserVisibleInformEntity
import team.aliens.local_domain.repository.LocalUserRepository
import team.aliens.local_domain.usecase.UseCase

class LocalUserVisibleInformUseCase @Inject constructor(
    private val localUserRepository: LocalUserRepository,
): UseCase<Unit, UserVisibleInformEntity>() {
    override suspend fun execute(data: Unit): UserVisibleInformEntity {
        return localUserRepository.fetchUserVisible()
    }
}