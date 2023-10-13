package team.aliens.dms.android.domain.usecase.remain

import team.aliens.dms.android.domain.model.remains.CurrentAppliedRemainsOption
import team.aliens.dms.android.domain.model.remains.toModel
import team.aliens.dms.android.domain.repository.RemainsRepository
import javax.inject.Inject

class FetchCurrentAppliedRemainsOptionUseCase @Inject constructor(
    private val remainsRepository: RemainsRepository,
) {
    suspend operator fun invoke(): CurrentAppliedRemainsOption {
        return remainsRepository.fetchCurrentAppliedRemainsOption().toModel()
    }
}
