package team.aliens.domain.usecase.remain

import team.aliens.domain._model.remains.UpdateRemainsOptionInput
import team.aliens.domain._repository.RemainsRepository
import javax.inject.Inject

class UpdateRemainsOptionUseCase @Inject constructor(
    private val remainsRepository: RemainsRepository,
) {
    suspend operator fun invoke(
        updateRemainsOptionInput: UpdateRemainsOptionInput,
    ) {
        remainsRepository.updateRemainsOption(
            input = updateRemainsOptionInput,
        )
    }
}
