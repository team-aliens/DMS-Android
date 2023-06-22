package team.aliens.domain.usecase.school

import team.aliens.domain.model.student.Features
import team.aliens.domain.repository.SchoolRepository
import javax.inject.Inject

class SaveAvailableFeaturesUseCase @Inject constructor(
    private val schoolRepository: SchoolRepository,
) {
    suspend operator fun invoke(features: Features) {
        schoolRepository.saveFeatures(features)
    }
}
