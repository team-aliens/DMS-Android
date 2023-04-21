package team.aliens.remote.datasource

import team.aliens.data._datasource.remote.RemoteSchoolDataSource
import team.aliens.domain._model.school.ExamineSchoolVerificationCodeInput
import team.aliens.domain._model.school.ExamineSchoolVerificationCodeOutput
import team.aliens.domain._model.school.ExamineSchoolVerificationQuestionInput
import team.aliens.domain._model.school.FetchAvailableFeaturesOutput
import team.aliens.domain._model.school.FetchSchoolVerificationQuestionOutput
import team.aliens.domain._model.school.FetchSchoolsOutput
import team.aliens.remote.model.school.toDomain
import team.aliens.remote.service.SchoolService
import team.aliens.remote.util.sendHttpRequest
import java.util.UUID
import javax.inject.Inject

class RemoteSchoolDataSourceImpl @Inject constructor(
    private val schoolService: SchoolService,
) : RemoteSchoolDataSource {

    override suspend fun fetchSchools(): FetchSchoolsOutput {
        return sendHttpRequest {
            schoolService.fetchSchools()
        }.toDomain()
    }

    override suspend fun fetchSchoolVerificationQuestion(
        schoolId: UUID,
    ): FetchSchoolVerificationQuestionOutput {
        return sendHttpRequest {
            schoolService.fetchSchoolVerificationQuestion(
                schoolId = schoolId,
            )
        }.toDomain()
    }

    override suspend fun examineSchoolVerificationQuestion(
        input: ExamineSchoolVerificationQuestionInput,
    ) {
        return sendHttpRequest {
            schoolService.examineSchoolVerificationQuestion(
                schoolId = input.schoolId,
                answer = input.answer,
            )
        }
    }

    override suspend fun examineSchoolVerificationCode(
        input: ExamineSchoolVerificationCodeInput,
    ): ExamineSchoolVerificationCodeOutput {
        return sendHttpRequest {
            schoolService.examineSchoolVerificationCode(
                schoolCode = input.schoolCode,
            )
        }.toDomain()
    }

    override suspend fun fetchAvailableFeatures(): FetchAvailableFeaturesOutput {
        return sendHttpRequest {
            schoolService.fetchAvailableFeatures()
        }.toDomain()
    }
}
