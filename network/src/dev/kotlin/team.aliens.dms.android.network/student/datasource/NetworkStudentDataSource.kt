package team.aliens.dms.android.network.student.datasource

import team.aliens.dms.android.network.student.model.EditProfileRequest
import team.aliens.dms.android.network.student.model.ExamineStudentNumberResponse
import team.aliens.dms.android.network.student.model.FetchMyPageResponse
import team.aliens.dms.android.network.student.model.FetchStudentsResponse
import team.aliens.dms.android.network.student.model.FindIdResponse
import team.aliens.dms.android.network.student.model.ResetPasswordRequest
import team.aliens.dms.android.network.student.model.SignUpRequest
import team.aliens.dms.android.network.student.model.SignUpResponse
import java.util.UUID

abstract class NetworkStudentDataSource {

    abstract suspend fun signUp(request: SignUpRequest): Result<SignUpResponse>

    abstract suspend fun examineStudentNumber(
        schoolId: UUID,
        grade: Int,
        classroom: Int,
        number: Int,
    ): Result<ExamineStudentNumberResponse>

    abstract suspend fun findId(
        schoolId: UUID,
        studentName: String,
        grade: Int,
        classRoom: Int,
        number: Int,
    ): Result<FindIdResponse>

    abstract suspend fun resetPassword(request: ResetPasswordRequest):Result<Unit>

    abstract suspend fun checkIdDuplication(id: String): Result<Unit>

    abstract suspend fun checkEmailDuplication(email: String): Result<Unit>

    abstract suspend fun fetchMyPage(): Result<FetchMyPageResponse>

    abstract suspend fun editProfile(request: EditProfileRequest): Result<Unit>

    abstract suspend fun withdraw(): Result<Unit>

    abstract suspend fun fetchStudents(): Result<FetchStudentsResponse>
}
