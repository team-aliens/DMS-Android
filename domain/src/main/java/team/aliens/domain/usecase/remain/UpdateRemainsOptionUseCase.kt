package team.aliens.domain.usecase.remain

import team.aliens.domain._repository.RemainsRepository
import java.util.UUID
import javax.inject.Inject

class UpdateRemainsOptionUseCase @Inject constructor(
    private val remainsRepository: RemainsRepository,
) {
    suspend operator fun invoke(
        remainsOptionId: UUID,
    ) {
        remainsRepository.updateRemainsOption(
            remainsOptionId = remainsOptionId,
        )
    }
}
