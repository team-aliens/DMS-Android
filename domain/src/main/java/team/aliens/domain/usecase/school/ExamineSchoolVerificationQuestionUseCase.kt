package team.aliens.domain.usecase.school

import team.aliens.domain._repository.SchoolRepository
import java.util.UUID
import javax.inject.Inject

class ExamineSchoolVerificationQuestionUseCase @Inject constructor(
    private val schoolRepository: SchoolRepository,
) {
    suspend operator fun invoke(
        schoolId: UUID,
        answer: String,
    ) {
        schoolRepository.examineSchoolVerificationQuestion(
            schoolId = schoolId,
            answer = answer,
        )
    }
}
