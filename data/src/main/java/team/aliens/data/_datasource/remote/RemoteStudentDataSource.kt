package team.aliens.data._datasource.remote

import team.aliens.domain._model.auth.AuthenticationOutput
import team.aliens.domain._model.student.EditProfileInput
import team.aliens.domain._model.student.FetchMyPageOutput
import team.aliens.domain._model.student.FindIdOutput
import team.aliens.domain._model.student.ResetPasswordInput
import team.aliens.domain._model.student.SignUpInput
import java.util.*

interface RemoteStudentDataSource {

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
