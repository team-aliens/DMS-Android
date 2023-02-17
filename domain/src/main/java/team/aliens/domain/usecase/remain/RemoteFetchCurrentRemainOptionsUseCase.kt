package team.aliens.domain.usecase.remain

import team.aliens.domain.entity.remain.CurrentRemainOptionEntity
import team.aliens.domain.repository.RemainRepository
import team.aliens.domain.usecase.UseCase
import javax.inject.Inject

class RemoteFetchCurrentRemainOptionsUseCase @Inject constructor(
    private val remainRepository: RemainRepository,
) : UseCase<Unit, CurrentRemainOptionEntity>() {
    override suspend fun execute(data: Unit): CurrentRemainOptionEntity {
        return remainRepository.fetchCurrentRemainOption()
    }
}
