package team.aliens.dms.android.domain.usecase.school

import team.aliens.dms.android.domain.model.student.Features
import team.aliens.dms.android.domain.repository.SchoolRepository
import javax.inject.Inject

class FetchLocalAvailableFeaturesUseCase @Inject constructor(
    private val schoolRepository: SchoolRepository,
) {
    suspend operator fun invoke(): Features {
        return schoolRepository.findFeatures()
    }
}
