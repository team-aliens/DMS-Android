package team.aliens.domain.usecase.remain

import team.aliens.domain._model.remains.FetchCurrentAppliedRemainsOptionOutput
import team.aliens.domain._repository.RemainsRepository
import javax.inject.Inject

class FetchCurrentAppliedRemainsOptionUseCase @Inject constructor(
    private val remainsRepository: RemainsRepository,
) {
    suspend operator fun invoke(): FetchCurrentAppliedRemainsOptionOutput {
        return remainsRepository.fetchCurrentAppliedRemainsOption()
    }
}
