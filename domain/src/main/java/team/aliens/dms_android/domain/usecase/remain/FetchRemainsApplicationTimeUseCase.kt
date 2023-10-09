package team.aliens.dms_android.domain.usecase.remain

import team.aliens.domain.model.remains.RemainsApplicationTime
import team.aliens.domain.model.remains.toModel
import team.aliens.domain.repository.RemainsRepository
import javax.inject.Inject

class FetchRemainsApplicationTimeUseCase @Inject constructor(
    private val remainsRepository: RemainsRepository,
) {
    suspend operator fun invoke(): RemainsApplicationTime {
        return remainsRepository.fetchRemainsApplicationTime().toModel()
    }
}
