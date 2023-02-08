package team.aliens.domain.usecase.schools

import com.example.domain.entity.user.SchoolConfirmQuestionEntity
import com.example.domain.repository.SchoolsRepository
import com.example.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class RemoteSchoolQuestionUseCase @Inject constructor(
    private val schoolsRepository: SchoolsRepository,
) : UseCase<UUID, Flow<SchoolConfirmQuestionEntity>>() {
    override suspend fun execute(data: UUID): Flow<SchoolConfirmQuestionEntity> {
        return schoolsRepository.schoolQuestion(data)
    }
}