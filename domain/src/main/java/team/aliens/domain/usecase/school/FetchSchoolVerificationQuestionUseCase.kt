package team.aliens.domain.usecase.school

import team.aliens.domain.model.school.FetchSchoolVerificationQuestionInput
import team.aliens.domain.model.school.FetchSchoolVerificationQuestionOutput
import team.aliens.domain.repository.SchoolRepository
import javax.inject.Inject

class FetchSchoolVerificationQuestionUseCase @Inject constructor(
    private val schoolRepository: SchoolRepository,
) {
    suspend operator fun invoke(
        fetchSchoolVerificationQuestionInput: FetchSchoolVerificationQuestionInput,
    ): FetchSchoolVerificationQuestionOutput {
        return schoolRepository.fetchSchoolVerificationQuestion(
            input = fetchSchoolVerificationQuestionInput,
        )
    }
}
