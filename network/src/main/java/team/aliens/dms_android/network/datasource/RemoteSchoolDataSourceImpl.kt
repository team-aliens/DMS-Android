package team.aliens.dms_android.network.datasource

import team.aliens.data.datasource.remote.RemoteSchoolDataSource
import team.aliens.domain.model.school.ExamineSchoolVerificationCodeInput
import team.aliens.domain.model.school.ExamineSchoolVerificationCodeOutput
import team.aliens.domain.model.school.ExamineSchoolVerificationQuestionInput
import team.aliens.domain.model.school.FetchAvailableFeaturesOutput
import team.aliens.domain.model.school.FetchSchoolVerificationQuestionInput
import team.aliens.domain.model.school.FetchSchoolVerificationQuestionOutput
import team.aliens.domain.model.school.FetchSchoolsOutput
import team.aliens.network.model.school.toDomain
import team.aliens.network.apiservice.SchoolApiService
import team.aliens.network.util.sendHttpRequest
import javax.inject.Inject

class RemoteSchoolDataSourceImpl @Inject constructor(
    private val schoolApiService: SchoolApiService,
) : RemoteSchoolDataSource {

    override suspend fun fetchSchools(): FetchSchoolsOutput {
        return sendHttpRequest {
            schoolApiService.fetchSchools()
        }.toDomain()
    }

    override suspend fun fetchSchoolVerificationQuestion(
        input: FetchSchoolVerificationQuestionInput,
    ): FetchSchoolVerificationQuestionOutput {
        return sendHttpRequest {
            schoolApiService.fetchSchoolVerificationQuestion(
                schoolId = input.schoolId,
            )
        }.toDomain()
    }

    override suspend fun examineSchoolVerificationQuestion(
        input: ExamineSchoolVerificationQuestionInput,
    ) {
        return sendHttpRequest {
            schoolApiService.examineSchoolVerificationQuestion(
                schoolId = input.schoolId,
                answer = input.answer,
            )
        }
    }

    override suspend fun examineSchoolVerificationCode(
        input: ExamineSchoolVerificationCodeInput,
    ): ExamineSchoolVerificationCodeOutput {
        return sendHttpRequest {
            schoolApiService.examineSchoolVerificationCode(
                schoolCode = input.schoolCode,
            )
        }.toDomain()
    }

    override suspend fun fetchAvailableFeatures(): FetchAvailableFeaturesOutput {
        return sendHttpRequest {
            schoolApiService.fetchAvailableFeatures()
        }.toDomain()
    }
}
