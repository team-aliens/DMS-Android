package team.aliens.domain.usecase.schools

import team.aliens.domain._model.school.ExamineSchoolVerificationCodeOutput
import team.aliens.domain._repository.SchoolRepository
import javax.inject.Inject

class ExamineSchoolVerificationCodeUseCase @Inject constructor(
    private val schoolRepository: SchoolRepository,
) {
    suspend operator fun invoke(
        schoolCode: String,
    ): ExamineSchoolVerificationCodeOutput {
        return schoolRepository.examineSchoolVerificationCode(
            schoolCode = schoolCode,
        )
    }
}
