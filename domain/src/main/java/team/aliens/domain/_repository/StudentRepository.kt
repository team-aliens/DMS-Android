package team.aliens.domain._repository

import team.aliens.domain._model.auth.TokenResult
import team.aliens.domain._model.student.*
import java.util.*

interface StudentRepository {

    suspend fun signUp(
        input: SignUpInput,
    ): TokenResult

    suspend fun verifyStudentNumber(
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
    ): FindIdResult

    suspend fun resetPassword(
        input: ResetPasswordInput,
    )

    suspend fun checkIdDuplication(
        accountId: String,
    )

    suspend fun checkEmailDuplication(
        email: String,
    )

    suspend fun queryMyPage(): QueryMyPageResult

    suspend fun editProfile(
        input: EditProfileInput,
    )

    suspend fun withdraw()
}
