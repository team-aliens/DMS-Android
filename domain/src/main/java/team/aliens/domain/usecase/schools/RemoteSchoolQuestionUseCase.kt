package team.aliens.domain.usecase.schools

import kotlinx.coroutines.flow.Flow
import team.aliens.domain.entity.user.SchoolConfirmQuestionEntity
import team.aliens.domain.repository.SchoolsRepository
import team.aliens.domain.usecase.UseCase
import java.util.*
import javax.inject.Inject

class RemoteSchoolQuestionUseCase @Inject constructor(
    private val schoolsRepository: SchoolsRepository,
) : UseCase<UUID, Flow<SchoolConfirmQuestionEntity>>() {
    override suspend fun execute(data: UUID): Flow<SchoolConfirmQuestionEntity> {
        return schoolsRepository.schoolQuestion(data)
    }
}