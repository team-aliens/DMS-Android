package team.aliens.dms.android.data.school.repository

import team.aliens.dms.android.data.school.model.School
import team.aliens.dms.android.data.school.model.SchoolId
import team.aliens.dms.android.network.school.datasource.NetworkSchoolDataSource
import java.util.UUID
import javax.inject.Inject

internal class SchoolRepositoryImpl @Inject constructor(
    private val networkSchoolDataSource: NetworkSchoolDataSource,
) : SchoolRepository() {

    override suspend fun fetchSchools(): List<School> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchSchoolVerificationQuestion(schoolId: UUID): String {
        TODO("Not yet implemented")
    }

    override suspend fun examineSchoolVerificationQuestion(
        schoolId: UUID,
        answer: String,
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun examineSchoolVerificationCode(code: String): SchoolId {
        TODO("Not yet implemented")
    }
}
