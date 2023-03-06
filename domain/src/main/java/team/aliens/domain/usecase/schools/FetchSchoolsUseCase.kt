package team.aliens.domain.usecase.schools

import team.aliens.domain.entity.schools.SchoolEntity
import team.aliens.domain.repository.SchoolsRepository
import team.aliens.domain.usecase.UseCase
import javax.inject.Inject

class FetchSchoolsUseCase @Inject constructor(
    private val schoolsRepository: SchoolsRepository,
) : UseCase<Unit, List<SchoolEntity>>() {
    override suspend fun execute(data: Unit): List<SchoolEntity> {
        return schoolsRepository.fetchSchools()
    }
}
