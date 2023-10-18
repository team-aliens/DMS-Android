package team.aliens.dms.android.data.student.repository

import team.aliens.dms.android.data.student.model.ExamineStudentNumberOutput
import team.aliens.dms.android.data.student.model.FindIdOutput
import team.aliens.dms.android.data.student.model.MyPage
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
        profileImageUrl: String? = null,
    )

    abstract suspend fun examineStudentNumber(
        schoolId: UUID,
        grade: Int,
        classRoom: Int,
        number: Int,
    ): ExamineStudentNumberOutput

    abstract suspend fun findId(
        schoolId: UUID,
        studentName: String,
        grade: Int,
        classRoom: Int,
        number: Int,
    ): FindIdOutput

    abstract suspend fun resetPassword(
        accountId: String,
        studentName: String,
        email: String,
        emailVerificationCode: String,
        newPassword: String,
    )

    abstract suspend fun checkIdDuplication(id: String)

    abstract suspend fun checkEmailDuplication(email: String)

    abstract suspend fun fetchMyPage(): MyPage

    abstract suspend fun editProfile(profileImageUrl: String)

    abstract suspend fun withdraw()
}
