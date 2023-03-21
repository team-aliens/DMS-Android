package team.aliens.domain._repository

import team.aliens.domain._model.school.ExamineSchoolVerificationCodeResult
import team.aliens.domain._model.school.QueryAvailableFeaturesResult
import team.aliens.domain._model.school.QuerySchoolVerificationQuestionResult
import team.aliens.domain._model.school.QuerySchoolsResult
import java.util.*

interface SchoolsRepository {

    suspend fun querySchools(): QuerySchoolsResult

    suspend fun querySchoolVerificationQuestion(): QuerySchoolVerificationQuestionResult

    suspend fun examineSchoolVerificationQuestion(
        schoolId: UUID,
        answer: String,
    )

    suspend fun examineSchoolVerificationCode(
        schoolCode: String,
    ): ExamineSchoolVerificationCodeResult

    suspend fun queryAvailableFeatures(): QueryAvailableFeaturesResult
}
