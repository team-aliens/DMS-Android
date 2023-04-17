package team.aliens.domain.usecase.schools

import team.aliens.domain._model.school.FetchSchoolVerificationQuestionOutput
import team.aliens.domain._repository.SchoolRepository
import java.util.UUID
import javax.inject.Inject

class FetchSchoolQuestionUseCase @Inject constructor(
    private val schoolRepository: SchoolRepository,
) {
    suspend operator fun invoke(
        schoolId: UUID,
    ): FetchSchoolVerificationQuestionOutput {
        return schoolRepository.fetchSchoolVerificationQuestion(
            schoolId = schoolId,
        )
    }
}
