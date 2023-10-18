package team.aliens.dms.android.data.school.repository

import team.aliens.dms.android.data.school.model.ExamineSchoolVerificationCodeOutput
import team.aliens.dms.android.data.school.model.FetchSchoolVerificationQuestionOutput
import team.aliens.dms.android.data.school.model.School
import java.util.UUID

abstract class SchoolRepository {

    abstract suspend fun fetchSchools(): List<School>

    abstract suspend fun fetchSchoolVerificationQuestion(schoolId: UUID): FetchSchoolVerificationQuestionOutput

    abstract suspend fun examineSchoolVerificationQuestion(
        schoolId: UUID,
        answer: String,
    )

    abstract suspend fun examineSchoolVerificationCode(code: String): ExamineSchoolVerificationCodeOutput
}
