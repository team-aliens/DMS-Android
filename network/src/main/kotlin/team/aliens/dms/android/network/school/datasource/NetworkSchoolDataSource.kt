package team.aliens.dms.android.network.school.datasource

import team.aliens.dms.android.network.school.model.ExamineSchoolVerificationCodeResponse
import team.aliens.dms.android.network.school.model.FetchSchoolVerificationQuestionResponse
import team.aliens.dms.android.network.school.model.FetchSchoolsResponse
import java.util.UUID

abstract class NetworkSchoolDataSource {

    abstract suspend fun fetchSchools(): FetchSchoolsResponse

    abstract suspend fun fetchSchoolVerificationQuestion(schoolId: UUID): FetchSchoolVerificationQuestionResponse

    abstract suspend fun examineSchoolVerificationQuestion(
        schoolId: UUID,
        answer: String,
    )

    abstract suspend fun examineSchoolVerificationCode(code: String): ExamineSchoolVerificationCodeResponse
}
