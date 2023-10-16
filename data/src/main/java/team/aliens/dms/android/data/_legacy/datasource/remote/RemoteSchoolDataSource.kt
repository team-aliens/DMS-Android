package team.aliens.dms.android.data._legacy.datasource.remote

import team.aliens.dms.android.domain.model.school.ExamineSchoolVerificationCodeInput
import team.aliens.dms.android.domain.model.school.ExamineSchoolVerificationCodeOutput
import team.aliens.dms.android.domain.model.school.ExamineSchoolVerificationQuestionInput
import team.aliens.dms.android.domain.model.school.FetchAvailableFeaturesOutput
import team.aliens.dms.android.domain.model.school.FetchSchoolVerificationQuestionInput
import team.aliens.dms.android.domain.model.school.FetchSchoolVerificationQuestionOutput
import team.aliens.dms.android.domain.model.school.FetchSchoolsOutput

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
