package team.aliens.dms.android.network.student.datasource
import team.aliens.dms.android.network.student.apiservice.StudentApiService
import team.aliens.dms.android.network.student.model.EditProfileRequest
import team.aliens.dms.android.network.student.model.ExamineStudentNumberResponse
import team.aliens.dms.android.network.student.model.FetchMyPageResponse
import team.aliens.dms.android.network.student.model.FetchStudentsResponse
import team.aliens.dms.android.network.student.model.FindIdResponse
import team.aliens.dms.android.network.student.model.ResetPasswordRequest
import team.aliens.dms.android.network.student.model.SignUpRequest
import team.aliens.dms.android.network.student.model.SignUpResponse
import team.aliens.dms.android.shared.exception.util.suspendRunCatching
import java.util.UUID
import javax.inject.Inject

internal class NetworkStudentDataSourceImpl @Inject constructor(
    private val studentApiService: StudentApiService,
) : NetworkStudentDataSource() {
    override suspend fun signUp(request: SignUpRequest): Result<SignUpResponse> =
        suspendRunCatching {
            studentApiService.signUp(request)
        }

    override suspend fun examineStudentNumber(
        schoolId: UUID,
        grade: Int,
        classroom: Int,
        number: Int,
    ): Result<ExamineStudentNumberResponse> = suspendRunCatching {
        studentApiService.examineStudentNumber(
            schoolId = schoolId,
            grade = grade,
            classRoom = classroom,
            number = number,
        )
    }

    override suspend fun findId(
        schoolId: UUID,
        studentName: String,
        grade: Int,
        classRoom: Int,
        number: Int,
    ): Result<FindIdResponse> =
        suspendRunCatching {
            studentApiService.findId(
                schoolId = schoolId,
                studentName = studentName,
                grade = grade,
                classRoom = classRoom,
                number = number,
            )
        }

    override suspend fun resetPassword(request: ResetPasswordRequest): Result<Unit> =
        suspendRunCatching {
            studentApiService.resetPassword(request)
        }

    override suspend fun checkIdDuplication(id: String): Result<Unit> =
        suspendRunCatching {
            studentApiService.checkIdDuplication(id)
        }

    override suspend fun checkEmailDuplication(email: String): Result<Unit> =
        suspendRunCatching {
            studentApiService.checkEmailDuplication(email)
        }

    override suspend fun fetchMyPage(): Result<FetchMyPageResponse> =
        suspendRunCatching {
            studentApiService.fetchMyPage()
        }

    override suspend fun editProfile(request: EditProfileRequest): Result<Unit> =
        suspendRunCatching {
            studentApiService.editProfile(request)
        }

    override suspend fun withdraw() = suspendRunCatching { studentApiService.withdraw() }
    override suspend fun fetchStudents(): Result<FetchStudentsResponse> =
        suspendRunCatching { studentApiService.fetchStudents() }
}
