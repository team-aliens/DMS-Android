package team.aliens.domain.usecase.school

import team.aliens.domain.model.student.Feature
import team.aliens.domain.repository.SchoolRepository
import javax.inject.Inject

class FetchLocalAvailableFeaturesUseCase @Inject constructor(
    private val schoolRepository: SchoolRepository,
) {
    suspend operator fun invoke(): Feature {
        return schoolRepository.findFeature()
    }
}
