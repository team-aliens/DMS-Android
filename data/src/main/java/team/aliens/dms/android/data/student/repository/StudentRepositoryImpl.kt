package team.aliens.dms.android.data.student.repository

import team.aliens.dms.android.data.student.model.HashedEmail
import team.aliens.dms.android.data.student.model.MyPage
import team.aliens.dms.android.data.student.model.StudentName
import team.aliens.dms.android.network.student.datasource.NetworkStudentDataSource
import java.util.UUID
import javax.inject.Inject

internal class StudentRepositoryImpl @Inject constructor(
    private val networkStudentDataSource: NetworkStudentDataSource,
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
        classRoom: Int,
        number: Int,
    ): StudentName {
        TODO("Not yet implemented")
    }

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
        TODO("Not yet implemented")
    }

    override suspend fun fetchMyPage(): MyPage {
        TODO("Not yet implemented")
    }

    override suspend fun editProfile(profileImageUrl: String) {
        TODO("Not yet implemented")
    }

    override suspend fun withdraw() {
        TODO("Not yet implemented")
    }
}
