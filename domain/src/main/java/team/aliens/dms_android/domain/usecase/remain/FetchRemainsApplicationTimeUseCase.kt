package team.aliens.dms_android.domain.usecase.remain

import team.aliens.dms_android.domain.model.remains.RemainsApplicationTime
import team.aliens.dms_android.domain.model.remains.toModel
import team.aliens.dms_android.domain.repository.RemainsRepository
import javax.inject.Inject

class FetchRemainsApplicationTimeUseCase @Inject constructor(
    private val remainsRepository: RemainsRepository,
) {
    suspend operator fun invoke(): RemainsApplicationTime {
        return remainsRepository.fetchRemainsApplicationTime().toModel()
    }
}
