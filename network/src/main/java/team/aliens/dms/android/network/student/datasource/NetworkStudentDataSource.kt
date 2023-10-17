package team.aliens.dms.android.network.student.datasource

import team.aliens.dms.android.core.jwt.network.model.TokensResponse
import team.aliens.dms.android.network.student.model.EditProfileRequest
import team.aliens.dms.android.network.student.model.ExamineStudentNumberResponse
import team.aliens.dms.android.network.student.model.FetchMyPageResponse
import team.aliens.dms.android.network.student.model.FindIdResponse
import team.aliens.dms.android.network.student.model.ResetPasswordRequest
import team.aliens.dms.android.network.student.model.SignUpRequest
import java.util.UUID

abstract class NetworkStudentDataSource {

    abstract suspend fun signUp(request: SignUpRequest): TokensResponse

    abstract suspend fun examineStudentNumber(
        schoolId: UUID,
        grade: Int,
        classRoom: Int,
        number: Int,
    ): ExamineStudentNumberResponse

    abstract suspend fun findId(
        schoolId: UUID,
        studentName: String,
        grade: Int,
        classRoom: Int,
        number: Int,
    ): FindIdResponse

    abstract suspend fun resetPassword(request: ResetPasswordRequest)

    abstract suspend fun checkIdDuplication(accountId: String)

    abstract suspend fun checkEmailDuplication(email: String)

    abstract suspend fun fetchMyPage(): FetchMyPageResponse

    abstract suspend fun editProfile(request: EditProfileRequest)

    abstract suspend fun withdraw()
}
