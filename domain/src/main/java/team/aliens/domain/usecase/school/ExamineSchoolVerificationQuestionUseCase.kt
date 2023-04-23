package team.aliens.domain.usecase.school

import team.aliens.domain._model.school.ExamineSchoolVerificationQuestionInput
import team.aliens.domain._repository.SchoolRepository
import javax.inject.Inject

class ExamineSchoolVerificationQuestionUseCase @Inject constructor(
    private val schoolRepository: SchoolRepository,
) {
    suspend operator fun invoke(
        examineSchoolVerificationQuestionInput: ExamineSchoolVerificationQuestionInput,
    ) {
        schoolRepository.examineSchoolVerificationQuestion(
            input = examineSchoolVerificationQuestionInput,
        )
    }
}
