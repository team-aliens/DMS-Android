package team.aliens.data._datasource.remote

import team.aliens.domain._model._common.AuthenticationOutput
import team.aliens.domain._model.student.CheckEmailDuplicationInput
import team.aliens.domain._model.student.CheckIdDuplicationInput
import team.aliens.domain._model.student.EditProfileInput
import team.aliens.domain._model.student.ExamineStudentNumberInput
import team.aliens.domain._model.student.ExamineStudentNumberOutput
import team.aliens.domain._model.student.FetchMyPageOutput
import team.aliens.domain._model.student.FindIdOutput
import team.aliens.domain._model.student.ResetPasswordInput
import team.aliens.domain._model.student.SignUpInput
import java.util.UUID

interface RemoteStudentDataSource {

    suspend fun signUp(
        input: SignUpInput,
    ): AuthenticationOutput

    suspend fun examineStudentNumber(
        input: ExamineStudentNumberInput,
    ): ExamineStudentNumberOutput

    suspend fun findId(
        schoolId: UUID,
        studentName: String,
        grade: Int,
        classRoom: Int,
        number: Int,
    ): FindIdOutput

    suspend fun resetPassword(
        input: ResetPasswordInput,
    )

    suspend fun checkIdDuplication(
        input: CheckIdDuplicationInput,
    )

    suspend fun checkEmailDuplication(
        input: CheckEmailDuplicationInput,
    )

    suspend fun fetchMyPage(): FetchMyPageOutput

    suspend fun editProfile(
        input: EditProfileInput,
    )

    suspend fun withdraw()
}
