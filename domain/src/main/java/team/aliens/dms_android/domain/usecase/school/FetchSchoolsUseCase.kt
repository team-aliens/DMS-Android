package team.aliens.dms_android.domain.usecase.school

import team.aliens.domain.model.school.FetchSchoolsOutput
import team.aliens.domain.repository.SchoolRepository
import javax.inject.Inject

class FetchSchoolsUseCase @Inject constructor(
    private val schoolRepository: SchoolRepository,
) {
    suspend operator fun invoke(): FetchSchoolsOutput {
        return schoolRepository.fetchSchools()
    }
}
