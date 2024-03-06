package team.aliens.dms.android.data.student.repository

import team.aliens.dms.android.core.jwt.JwtProvider
import team.aliens.dms.android.core.school.SchoolProvider
import team.aliens.dms.android.data.student.mapper.toModel
import team.aliens.dms.android.data.student.model.HashedEmail
import team.aliens.dms.android.data.student.model.MyPage
import team.aliens.dms.android.data.student.model.StudentName
import team.aliens.dms.android.network.student.datasource.NetworkStudentDataSource
import java.util.UUID
import javax.inject.Inject

internal class StudentRepositoryImpl @Inject constructor(
    private val networkStudentDataSource: NetworkStudentDataSource,
    private val jwtProvider: JwtProvider,
    private val schoolProvider: SchoolProvider,
) : StudentRepository() {

    override suspend fun signUp(
        schoolVerificationCode: String,
        schoolVerificationAnswer: String,
        email: String,
        emailVerificationCode: String,
        grade: Int,
        classRoom: Int,
        number: Int,
        accountId: String,
        password: String,
        profileImageUrl: String?,
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun examineStudentNumber(
        schoolId: UUID,
        grade: Int,
        classroom: Int,
        number: Int,
    ): StudentName = networkStudentDataSource.examineStudentNumber(
        schoolId = schoolId,
        grade = grade,
        classroom = classroom,
        number = number,
    ).studentName

    override suspend fun findId(
        schoolId: UUID,
        studentName: String,
        grade: Int,
        classRoom: Int,
        number: Int,
    ): HashedEmail {
        TODO("Not yet implemented")
    }

    override suspend fun resetPassword(
        accountId: String,
        studentName: String,
        email: String,
        emailVerificationCode: String,
        newPassword: String,
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun checkIdDuplication(id: String) {
        TODO("Not yet implemented")
    }

    override suspend fun checkEmailDuplication(email: String) {
        networkStudentDataSource.checkEmailDuplication(email = email)
    }

    override suspend fun fetchMyPage(): MyPage = networkStudentDataSource.fetchMyPage().toModel()

    override suspend fun editProfile(profileImageUrl: String) {
        TODO("Not yet implemented")
    }

    override suspend fun withdraw() {
        // TODO: remove docs
        /*networkStudentDataSource.withdraw()
        jwtProvider.clearCaches()
        schoolProvider.clearCaches()*/
    }
}
