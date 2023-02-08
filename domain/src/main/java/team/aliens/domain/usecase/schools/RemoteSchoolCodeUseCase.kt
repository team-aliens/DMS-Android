package team.aliens.domain.usecase.schools

import com.example.domain.entity.user.SchoolIdEntity
import com.example.domain.repository.SchoolsRepository
import com.example.domain.usecase.UseCase
import javax.inject.Inject

class RemoteSchoolCodeUseCase @Inject constructor(
    private val schoolsRepository: SchoolsRepository
) : UseCase<String, SchoolIdEntity>() {
    override suspend fun execute(data: String): SchoolIdEntity =
        schoolsRepository.examineSchoolCode(data)
}