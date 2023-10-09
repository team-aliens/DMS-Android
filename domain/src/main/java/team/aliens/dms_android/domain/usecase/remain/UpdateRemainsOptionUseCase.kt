package team.aliens.dms_android.domain.usecase.remain

import team.aliens.domain.model.remains.UpdateRemainsOptionInput
import team.aliens.domain.repository.RemainsRepository
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
