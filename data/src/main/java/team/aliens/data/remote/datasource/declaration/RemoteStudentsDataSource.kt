package team.aliens.data.remote.datasource.declaration

import com.example.data.remote.request.students.ResetPasswordRequest
import com.example.data.remote.request.students.SignUpRequest
import com.example.data.remote.response.students.ExamineGradeResponse
import java.util.UUID

interface RemoteStudentsDataSource {
    suspend fun postUserSignUp(
        signUpRequest: SignUpRequest,
    )

    suspend fun duplicateCheckId(
        accountId: String
    )

    suspend fun duplicateCheckEmail(
        email: String
    )

    suspend fun resetPassword(
        resetPasswordRequest: ResetPasswordRequest
    )

    suspend fun examineGrade(
        schoolId: UUID,
        grade: Int,
        classRoom: Int,
        number: Int,
    ): ExamineGradeResponse
}