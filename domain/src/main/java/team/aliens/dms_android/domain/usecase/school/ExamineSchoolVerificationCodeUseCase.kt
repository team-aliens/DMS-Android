package team.aliens.dms_android.domain.usecase.school

import team.aliens.dms_android.domain.model.school.ExamineSchoolVerificationCodeInput
import team.aliens.dms_android.domain.model.school.ExamineSchoolVerificationCodeOutput
import team.aliens.dms_android.domain.repository.SchoolRepository
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
