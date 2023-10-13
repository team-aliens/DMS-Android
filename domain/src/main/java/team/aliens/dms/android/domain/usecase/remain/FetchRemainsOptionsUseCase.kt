package team.aliens.dms.android.domain.usecase.remain

import team.aliens.dms.android.domain.model.remains.RemainsOption
import team.aliens.dms.android.domain.model.remains.toModel
import team.aliens.dms.android.domain.repository.RemainsRepository
import javax.inject.Inject

class FetchRemainsOptionsUseCase @Inject constructor(
    private val remainsRepository: RemainsRepository,
) {
    suspend operator fun invoke(): List<RemainsOption> {
        return remainsRepository.fetchRemainsOptions().toModel()
    }
}
