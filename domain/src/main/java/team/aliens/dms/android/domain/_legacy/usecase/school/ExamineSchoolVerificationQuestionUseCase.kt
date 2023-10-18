package team.aliens.dms.android.domain._legacy.usecase.school

import team.aliens.dms.android.domain.model.school.ExamineSchoolVerificationQuestionInput
import team.aliens.dms.android.domain.repository.SchoolRepository
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
