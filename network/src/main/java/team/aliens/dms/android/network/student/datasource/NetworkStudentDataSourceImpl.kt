package team.aliens.dms.android.network.student.datasource

import team.aliens.dms.android.core.network.util.handleNetworkRequest
import team.aliens.dms.android.network.student.apiservice.StudentApiService
import team.aliens.dms.android.network.student.model.EditProfileRequest
import team.aliens.dms.android.network.student.model.ExamineStudentNumberResponse
import team.aliens.dms.android.network.student.model.FetchMyPageResponse
import team.aliens.dms.android.network.student.model.FindIdResponse
import team.aliens.dms.android.network.student.model.ResetPasswordRequest
import team.aliens.dms.android.network.student.model.SignUpRequest
import team.aliens.dms.android.network.student.model.SignUpResponse
import java.util.UUID
import javax.inject.Inject

internal class NetworkStudentDataSourceImpl @Inject constructor(
    private val studentApiService: StudentApiService,
) : NetworkStudentDataSource() {
    override suspend fun signUp(request: SignUpRequest): SignUpResponse =
        handleNetworkRequest { studentApiService.signUp(request) }

    override suspend fun examineStudentNumber(
        schoolId: UUID,
        grade: Int,
        classroom: Int,
        number: Int,
    ): ExamineStudentNumberResponse = studentApiService.examineStudentNumber(
        schoolId = schoolId,
        grade = grade,
        classRoom = classroom,
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

    override suspend fun resetPassword(request: ResetPasswordRequest): Unit =
        handleNetworkRequest { studentApiService.resetPassword(request) }

    override suspend fun checkIdDuplication(id: String) =
        handleNetworkRequest { studentApiService.checkIdDuplication(id) }

    override suspend fun checkEmailDuplication(email: String) =
        handleNetworkRequest { studentApiService.checkEmailDuplication(email) }

    override suspend fun fetchMyPage(): FetchMyPageResponse =
        handleNetworkRequest { studentApiService.fetchMyPage() }

    override suspend fun editProfile(request: EditProfileRequest): Unit =
        handleNetworkRequest { studentApiService.editProfile(request) }

    override suspend fun withdraw() = handleNetworkRequest { studentApiService.withdraw() }
}
