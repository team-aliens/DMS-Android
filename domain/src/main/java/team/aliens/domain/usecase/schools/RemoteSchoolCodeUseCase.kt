package team.aliens.domain.usecase.schools

import team.aliens.domain.entity.user.SchoolIdEntity
import team.aliens.domain.repository.SchoolsRepository
import team.aliens.domain.usecase.UseCase
import javax.inject.Inject

class RemoteSchoolCodeUseCase @Inject constructor(
    private val schoolsRepository: SchoolsRepository,
) : UseCase<String, SchoolIdEntity>() {
    override suspend fun execute(data: String): SchoolIdEntity =
        schoolsRepository.examineSchoolCode(data)
}