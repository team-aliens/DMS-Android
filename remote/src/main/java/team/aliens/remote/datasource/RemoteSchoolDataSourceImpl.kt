package team.aliens.remote.datasource

import team.aliens.data._datasource.remote.RemoteSchoolDataSource
import team.aliens.domain._model.school.ExamineSchoolVerificationCodeOutput
import team.aliens.domain._model.school.FetchAvailableFeaturesOutput
import team.aliens.domain._model.school.FetchSchoolVerificationQuestionOutput
import team.aliens.domain._model.school.FetchSchoolsOutput
import java.util.*

class RemoteSchoolDataSourceImpl : RemoteSchoolDataSource {

    override suspend fun fetchSchools(): FetchSchoolsOutput {
        TODO("Not yet implemented")
    }

    override suspend fun fetchSchoolVerificationQuestion(): FetchSchoolVerificationQuestionOutput {
        TODO("Not yet implemented")
    }

    override suspend fun examineSchoolVerificationQuestion(
        schoolId: UUID,
        answer: String,
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun examineSchoolVerificationCode(
        schoolCode: String,
    ): ExamineSchoolVerificationCodeOutput {
        TODO("Not yet implemented")
    }

    override suspend fun fetchAvailableFeatures(): FetchAvailableFeaturesOutput {
        TODO("Not yet implemented")
    }
}
