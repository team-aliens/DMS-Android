package team.aliens.domain.usecase.remain

import team.aliens.domain.entity.remain.AvailableRemainTimeEntity
import team.aliens.domain.repository.RemainRepository
import team.aliens.domain.usecase.UseCase
import javax.inject.Inject

class RemoteFetchAvailableRemainTimeUseCase @Inject constructor(
    private val remainRepository: RemainRepository,
) : UseCase<Unit, AvailableRemainTimeEntity>() {
    override suspend fun execute(data: Unit): AvailableRemainTimeEntity {
        return remainRepository.fetchAvailableRemainTime()
    }
}
