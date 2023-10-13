package team.aliens.dms.android.domain.usecase.school

import team.aliens.dms.android.domain.model.school.FetchSchoolsOutput
import team.aliens.dms.android.domain.repository.SchoolRepository
import javax.inject.Inject

class FetchSchoolsUseCase @Inject constructor(
    private val schoolRepository: SchoolRepository,
) {
    suspend operator fun invoke(): FetchSchoolsOutput {
        return schoolRepository.fetchSchools()
    }
}
