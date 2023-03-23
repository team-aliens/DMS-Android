package team.aliens.domain._repository

import team.aliens.domain._model.school.ExamineSchoolVerificationCodeOutput
import team.aliens.domain._model.school.FetchAvailableFeaturesOutput
import team.aliens.domain._model.school.FetchSchoolVerificationQuestionOutput
import team.aliens.domain._model.school.FetchSchoolsOutput
import java.util.*

interface SchoolRepository {

    suspend fun querySchools(): FetchSchoolsOutput

    suspend fun querySchoolVerificationQuestion(): FetchSchoolVerificationQuestionOutput

    suspend fun examineSchoolVerificationQuestion(
        schoolId: UUID,
        answer: String,
    )

    suspend fun examineSchoolVerificationCode(
        schoolCode: String,
    ): ExamineSchoolVerificationCodeOutput

    suspend fun queryAvailableFeatures(): FetchAvailableFeaturesOutput
}
