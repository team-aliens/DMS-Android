package team.aliens.dms.android.data.student.repository

import team.aliens.dms.android.data.student.model.HashedEmail
import team.aliens.dms.android.data.student.model.MyPage
import team.aliens.dms.android.data.student.model.Student
import team.aliens.dms.android.data.student.model.StudentName
import team.aliens.dms.android.network.student.model.ExamineStudentNumberResponse
import team.aliens.dms.android.network.student.model.FindIdResponse
import java.util.UUID

abstract class StudentRepository {

    abstract suspend fun signUp(
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
    ): Result<Unit>

    abstract suspend fun examineStudentNumber(
        schoolId: UUID,
        grade: Int,
        classroom: Int,
        number: Int,
    ): Result<StudentName>

    abstract suspend fun findId(
        schoolId: UUID,
        studentName: String,
        grade: Int,
        classRoom: Int,
        number: Int,
    ): Result<HashedEmail>

    abstract suspend fun resetPassword(
        accountId: String,
        studentName: String,
        email: String,
        emailVerificationCode: String,
        newPassword: String,
    ): Result<Unit>

    abstract suspend fun checkIdDuplication(id: String): Result<Unit>

    abstract suspend fun checkEmailDuplication(email: String): Result<Unit>

    abstract suspend fun fetchMyPage(): Result<MyPage>

    abstract suspend fun editProfile(profileImageUrl: String): Result<Unit>

    abstract suspend fun withdraw(): Result<Unit>

    abstract suspend fun fetchStudents(): Result<List<Student>>
}
