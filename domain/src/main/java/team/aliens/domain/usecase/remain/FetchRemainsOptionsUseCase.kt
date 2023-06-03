package team.aliens.domain.usecase.remain

import team.aliens.domain.model.remains.RemainsOption
import team.aliens.domain.model.remains.toModel
import team.aliens.domain.repository.RemainsRepository
import javax.inject.Inject

class FetchRemainsOptionsUseCase @Inject constructor(
    private val remainsRepository: RemainsRepository,
) {
    suspend operator fun invoke(): List<RemainsOption> {
        return remainsRepository.fetchRemainsOptions().toModel()
    }
}
