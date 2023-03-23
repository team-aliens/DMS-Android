package team.aliens.data._datasource.remote

import team.aliens.domain._model.school.ExamineSchoolVerificationCodeOutput
import team.aliens.domain._model.school.FetchAvailableFeaturesOutput
import team.aliens.domain._model.school.FetchSchoolVerificationQuestionOutput
import team.aliens.domain._model.school.FetchSchoolsOutput
import java.util.*

interface RemoteSchoolDataSource {

    suspend fun fetchSchools(): FetchSchoolsOutput

    suspend fun fetchSchoolVerificationQuestion(): FetchSchoolVerificationQuestionOutput

    suspend fun examineSchoolVerificationQuestion(
        schoolId: UUID,
        answer: String,
    )

    suspend fun examineSchoolVerificationCode(
        schoolCode: String,
    ): ExamineSchoolVerificationCodeOutput

    suspend fun fetchAvailableFeatures(): FetchAvailableFeaturesOutput
}
