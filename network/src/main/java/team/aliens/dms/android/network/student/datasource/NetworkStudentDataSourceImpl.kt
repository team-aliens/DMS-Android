package team.aliens.dms.android.network.student.datasource

import team.aliens.dms.android.core.jwt.network.model.AuthenticationResponse
import team.aliens.dms.android.core.network.util.sendHttpRequest
import team.aliens.dms.android.network.student.apiservice.StudentApiService
import team.aliens.dms.android.network.student.model.EditProfileRequest
import team.aliens.dms.android.network.student.model.ExamineStudentNumberResponse
import team.aliens.dms.android.network.student.model.FetchMyPageResponse
import team.aliens.dms.android.network.student.model.FindIdResponse
import team.aliens.dms.android.network.student.model.ResetPasswordRequest
import team.aliens.dms.android.network.student.model.SignUpRequest
import java.util.UUID
import javax.inject.Inject

internal class NetworkStudentDataSourceImpl @Inject constructor(
    private val studentApiService: StudentApiService,
) : NetworkStudentDataSource() {
    override suspend fun signUp(request: SignUpRequest): AuthenticationResponse =
        sendHttpRequest { studentApiService.signUp(request) }

    override suspend fun examineStudentNumber(
        schoolId: UUID,
        grade: Int,
        classRoom: Int,
        number: Int,
    ): ExamineStudentNumberResponse = studentApiService.examineStudentNumber(
        schoolId = schoolId,
        grade = grade,
        classRoom = classRoom,
        number = number,
    )

    override suspend fun findId(
        schoolId: UUID,
        studentName: String,
        grade: Int,
        classRoom: Int,
        number: Int,
    ): FindIdResponse = studentApiService.findId(
        schoolId = schoolId,
        studentName = studentName,
        grade = grade,
        classRoom = classRoom,
        number = number,
    )

    override suspend fun resetPassword(request: ResetPasswordRequest) =
        sendHttpRequest { studentApiService.resetPassword(request) }

    override suspend fun checkIdDuplication(accountId: String) =
        sendHttpRequest { studentApiService.checkIdDuplication(accountId) }

    override suspend fun checkEmailDuplication(email: String) =
        sendHttpRequest { studentApiService.checkEmailDuplication(email) }

    override suspend fun fetchMyPage(): FetchMyPageResponse =
        sendHttpRequest { studentApiService.fetchMyPage() }

    override suspend fun editProfile(request: EditProfileRequest) =
        sendHttpRequest { studentApiService.editProfile(request) }

    override suspend fun withdraw() = sendHttpRequest { studentApiService.withdraw() }
}
