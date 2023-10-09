package team.aliens.dms_android.domain.usecase.school

import team.aliens.domain.model.school.ExamineSchoolVerificationQuestionInput
import team.aliens.domain.repository.SchoolRepository
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
