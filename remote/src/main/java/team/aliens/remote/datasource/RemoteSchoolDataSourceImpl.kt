package team.aliens.remote.datasource

import team.aliens.data.datasource.remote.RemoteSchoolDataSource
import team.aliens.domain._model.school.ExamineSchoolVerificationCodeInput
import team.aliens.domain._model.school.ExamineSchoolVerificationCodeOutput
import team.aliens.domain._model.school.ExamineSchoolVerificationQuestionInput
import team.aliens.domain._model.school.FetchAvailableFeaturesOutput
import team.aliens.domain._model.school.FetchSchoolVerificationQuestionInput
import team.aliens.domain._model.school.FetchSchoolVerificationQuestionOutput
import team.aliens.domain._model.school.FetchSchoolsOutput
import team.aliens.remote.model.school.toDomain
import team.aliens.remote.apiservice.SchoolApiService
import team.aliens.remote.util.sendHttpRequest
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
