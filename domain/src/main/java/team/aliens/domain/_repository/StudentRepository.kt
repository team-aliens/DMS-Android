package team.aliens.domain._repository

import team.aliens.domain._model._common.AuthenticationOutput
import team.aliens.domain._model.student.*
import java.util.*

interface StudentRepository {

    suspend fun signUp(
        input: SignUpInput,
    ): AuthenticationOutput

    suspend fun checkStudentNumber(
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
