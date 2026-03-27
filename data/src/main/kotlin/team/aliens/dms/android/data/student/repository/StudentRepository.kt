package team.aliens.dms.android.data.student.repository

import team.aliens.dms.android.data.student.model.HashedEmail
import team.aliens.dms.android.data.student.model.MyPage
import team.aliens.dms.android.data.student.model.Student
import team.aliens.dms.android.data.student.model.StudentName
import team.aliens.dms.android.network.student.model.ExamineStudentNumberResponse
import team.aliens.dms.android.network.student.model.FindIdResponse
import java.util.UUID

abstract class StudentRepository {

    data class SignUpParam(
        val schoolVerificationCode: String,
        val schoolVerificationAnswer: String,
        val email: String,
        val emailVerificationCode: String,
        val grade: Int,
        val classRoom: Int,
        val number: Int,
        val accountId: String,
        val password: String,
        val profileImageUrl: String?,
    )

    abstract suspend fun signUp(param: SignUpParam): Result<Unit>

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

    abstract suspend fun editPassword(
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
