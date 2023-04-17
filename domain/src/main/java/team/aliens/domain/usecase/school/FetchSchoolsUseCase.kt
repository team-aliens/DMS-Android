package team.aliens.domain.usecase.school

import team.aliens.domain._model.school.FetchSchoolsOutput
import team.aliens.domain._repository.SchoolRepository
import javax.inject.Inject

class FetchSchoolsUseCase @Inject constructor(
    private val schoolRepository: SchoolRepository,
) {
    suspend operator fun invoke(): FetchSchoolsOutput {
        return schoolRepository.fetchSchools()
    }
}
