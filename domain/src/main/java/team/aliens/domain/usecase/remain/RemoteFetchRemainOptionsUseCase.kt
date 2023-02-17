package team.aliens.domain.usecase.remain

import team.aliens.domain.entity.remain.RemainOptionsEntity
import team.aliens.domain.repository.RemainRepository
import team.aliens.domain.usecase.UseCase
import javax.inject.Inject

class RemoteFetchRemainOptionsUseCase @Inject constructor(
    private val remainRepository: RemainRepository,
) : UseCase<Unit, RemainOptionsEntity>() {
    override suspend fun execute(data: Unit): RemainOptionsEntity {
        return remainRepository.fetchRemainOptions()
    }
}
