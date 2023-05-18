package team.aliens.domain.usecase.remain

import team.aliens.domain.model.remains.FetchRemainsApplicationTimeOutput
import team.aliens.domain.repository.RemainsRepository
import javax.inject.Inject

class FetchRemainsApplicationTimeUseCase @Inject constructor(
    private val remainsRepository: RemainsRepository,
) {
    suspend operator fun invoke(): FetchRemainsApplicationTimeOutput {
        return remainsRepository.fetchRemainsApplicationTime()
    }
}
