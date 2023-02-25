package team.aliens.data.remote.datasource.declaration

import team.aliens.data.remote.request.students.EditProfileImageRequest
import team.aliens.data.remote.request.students.ResetPasswordRequest
import team.aliens.data.remote.request.students.SignUpRequest
import team.aliens.data.remote.response.students.ExamineGradeResponse
import java.util.*

interface RemoteStudentsDataSource {
    suspend fun postUserSignUp(
        signUpRequest: SignUpRequest,
    )

    suspend fun duplicateCheckId(
        accountId: String,
    )

    suspend fun duplicateCheckEmail(
        email: String,
    )

    suspend fun resetPassword(
        resetPasswordRequest: ResetPasswordRequest,
    )

    suspend fun examineGrade(
        schoolId: UUID,
        grade: Int,
        classRoom: Int,
        number: Int,
    ): ExamineGradeResponse

    suspend fun editProfileImage(
        editProfileImageRequest: EditProfileImageRequest,
    )
}