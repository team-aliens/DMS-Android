package team.aliens.data._datasource.remote

import team.aliens.domain._model.school.ExamineSchoolVerificationCodeInput
import team.aliens.domain._model.school.ExamineSchoolVerificationCodeOutput
import team.aliens.domain._model.school.ExamineSchoolVerificationQuestionInput
import team.aliens.domain._model.school.FetchAvailableFeaturesOutput
import team.aliens.domain._model.school.FetchSchoolVerificationQuestionInput
import team.aliens.domain._model.school.FetchSchoolVerificationQuestionOutput
import team.aliens.domain._model.school.FetchSchoolsOutput

interface RemoteSchoolDataSource {

    suspend fun fetchSchools(): FetchSchoolsOutput

    suspend fun fetchSchoolVerificationQuestion(
        input: FetchSchoolVerificationQuestionInput,
    ): FetchSchoolVerificationQuestionOutput

    suspend fun examineSchoolVerificationQuestion(
        input: ExamineSchoolVerificationQuestionInput,
    )

    suspend fun examineSchoolVerificationCode(
        input: ExamineSchoolVerificationCodeInput,
    ): ExamineSchoolVerificationCodeOutput

    suspend fun fetchAvailableFeatures(): FetchAvailableFeaturesOutput
}
