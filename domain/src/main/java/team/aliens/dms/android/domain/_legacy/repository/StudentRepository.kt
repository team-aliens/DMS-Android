package team.aliens.dms.android.domain._legacy.repository

import team.aliens.dms.android.domain._legacy.model._common.AuthenticationOutput
import team.aliens.dms.android.domain.model.student.CheckEmailDuplicationInput
import team.aliens.dms.android.domain.model.student.CheckIdDuplicationInput
import team.aliens.dms.android.domain.model.student.EditProfileInput
import team.aliens.dms.android.domain.model.student.ExamineStudentNumberInput
import team.aliens.dms.android.domain.model.student.ExamineStudentNumberOutput
import team.aliens.dms.android.domain.model.student.FetchMyPageOutput
import team.aliens.dms.android.domain.model.student.FindIdInput
import team.aliens.dms.android.domain.model.student.FindIdOutput
import team.aliens.dms.android.domain.model.student.ResetPasswordInput
import team.aliens.dms.android.domain.model.student.SignUpInput

interface StudentRepository {

    suspend fun signUp(
        input: SignUpInput,
    ): _root_ide_package_.team.aliens.dms.android.domain._legacy.model._common.AuthenticationOutput

    suspend fun examineStudentNumber(
        input: ExamineStudentNumberInput,
    ): ExamineStudentNumberOutput

    suspend fun findId(
        input: FindIdInput,
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
