package team.aliens.domain.usecase.remain

import team.aliens.domain.repository.RemainRepository
import team.aliens.domain.usecase.UseCase
import java.util.*
import javax.inject.Inject

class RemoteUpdateRemainOptionUseCase @Inject constructor(
    private val remainRepository: RemainRepository,
) : UseCase<UUID, Unit>() {
    override suspend fun execute(remainOptionId: UUID) {
        remainRepository.updateRemainOption(
            remainOptionId = remainOptionId,
        )
    }
}
