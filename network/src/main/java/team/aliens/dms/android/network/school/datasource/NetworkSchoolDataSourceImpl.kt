package team.aliens.dms.android.network.school.datasource

import team.aliens.dms.android.core.network.util.sendHttpRequest
import team.aliens.dms.android.network.school.apiservice.SchoolApiService
import team.aliens.dms.android.network.school.model.ExamineSchoolVerificationCodeResponse
import team.aliens.dms.android.network.school.model.FetchSchoolVerificationQuestionResponse
import team.aliens.dms.android.network.school.model.FetchSchoolsResponse
import java.util.UUID
import javax.inject.Inject

internal class NetworkSchoolDataSourceImpl @Inject constructor(
    private val schoolApiService: SchoolApiService,
) : NetworkSchoolDataSource() {
    override suspend fun fetchSchools(): FetchSchoolsResponse =
        sendHttpRequest { schoolApiService.fetchSchools() }

    override suspend fun fetchSchoolVerificationQuestion(schoolId: UUID): FetchSchoolVerificationQuestionResponse =
        sendHttpRequest { schoolApiService.fetchSchoolVerificationQuestion(schoolId) }

    override suspend fun examineSchoolVerificationQuestion(
        schoolId: UUID,
        answer: String,
    ) = sendHttpRequest { schoolApiService.examineSchoolVerificationQuestion(schoolId, answer) }

    override suspend fun examineSchoolVerificationCode(code: String): ExamineSchoolVerificationCodeResponse =
        sendHttpRequest { schoolApiService.examineSchoolVerificationCode(code) }
}
