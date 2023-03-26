package team.aliens.domain._repository

import team.aliens.domain._model._common.AuthenticationOutput
import team.aliens.domain._model.student.EditProfileInput
import team.aliens.domain._model.student.FetchMyPageOutput
import team.aliens.domain._model.student.FindIdOutput
import team.aliens.domain._model.student.ResetPasswordInput
import team.aliens.domain._model.student.SignUpInput
import java.util.*

interface StudentRepository {

    suspend fun signUp(
        input: SignUpInput,
    ): AuthenticationOutput

    suspend fun examineStudentNumber(
        schoolId: UUID,
        grade: Int,
        classRoom: Int,
        number: Int,
    )

    suspend fun findId(
        schoolId: UUID,
        name: String,
        grade: Int,
        classRoom: Int,
        number: Int,
    ): FindIdOutput

    suspend fun resetPassword(
        input: ResetPasswordInput,
    )

    suspend fun checkIdDuplication(
        accountId: String,
    )

    suspend fun checkEmailDuplication(
        email: String,
    )

    suspend fun fetchMyPage(): FetchMyPageOutput

    suspend fun editProfile(
        input: EditProfileInput,
    )

    suspend fun withdraw()
}
