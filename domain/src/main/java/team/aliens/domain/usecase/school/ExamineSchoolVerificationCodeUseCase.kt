package team.aliens.domain.usecase.school

import team.aliens.domain.model.school.ExamineSchoolVerificationCodeInput
import team.aliens.domain.model.school.ExamineSchoolVerificationCodeOutput
import team.aliens.domain.repository.SchoolRepository
import javax.inject.Inject

class ExamineSchoolVerificationCodeUseCase @Inject constructor(
    private val schoolRepository: SchoolRepository,
) {
    suspend operator fun invoke(
        examineSchoolVerificationCodeInput: ExamineSchoolVerificationCodeInput,
    ): ExamineSchoolVerificationCodeOutput {
        return schoolRepository.examineSchoolVerificationCode(
            input = examineSchoolVerificationCodeInput,
        )
    }
}
