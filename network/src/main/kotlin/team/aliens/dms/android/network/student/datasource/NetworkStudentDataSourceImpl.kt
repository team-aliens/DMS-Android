package team.aliens.dms.android.network.student.datasource
import team.aliens.dms.android.network.student.apiservice.StudentAuthApiService
import team.aliens.dms.android.network.student.apiservice.StudentProfileApiService
import team.aliens.dms.android.network.student.model.EditProfileRequest
import team.aliens.dms.android.network.student.model.ExamineStudentNumberResponse
import team.aliens.dms.android.network.student.model.FetchMyPageResponse
import team.aliens.dms.android.network.student.model.FetchStudentsResponse
import team.aliens.dms.android.network.student.model.FindIdResponse
import team.aliens.dms.android.network.student.model.EditPasswordRequest
import team.aliens.dms.android.network.student.model.SignUpRequest
import team.aliens.dms.android.network.student.model.SignUpResponse
import team.aliens.dms.android.shared.exception.util.suspendRunCatching
import java.util.UUID
import javax.inject.Inject

internal class NetworkStudentDataSourceImpl @Inject constructor(
    private val studentAuthApiService: StudentAuthApiService,
    private val studentProfileApiService: StudentProfileApiService,
) : NetworkStudentDataSource() {
    override suspend fun signUp(request: SignUpRequest): Result<SignUpResponse> =
        suspendRunCatching {
            studentAuthApiService.signUp(request)
        }

    override suspend fun examineStudentNumber(
        schoolId: UUID,
        grade: Int,
        classroom: Int,
        number: Int,
    ): Result<ExamineStudentNumberResponse> = suspendRunCatching {
        studentAuthApiService.examineStudentNumber(
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
            studentAuthApiService.findId(
                schoolId = schoolId,
                studentName = studentName,
                grade = grade,
                classRoom = classRoom,
                number = number,
            )
        }

    override suspend fun editPassword(request: EditPasswordRequest): Result<Unit> =
        suspendRunCatching {
            studentAuthApiService.editPassword(request)
        }

    override suspend fun checkIdDuplication(id: String): Result<Unit> =
        suspendRunCatching {
            studentAuthApiService.checkIdDuplication(id)
        }

    override suspend fun checkEmailDuplication(email: String): Result<Unit> =
        suspendRunCatching {
            studentAuthApiService.checkEmailDuplication(email)
        }

    override suspend fun fetchMyPage(): Result<FetchMyPageResponse> =
        suspendRunCatching {
            studentProfileApiService.fetchMyPage()
        }

    override suspend fun editProfile(request: EditProfileRequest): Result<Unit> =
        suspendRunCatching {
            studentProfileApiService.editProfile(request)
        }

    override suspend fun withdraw() = suspendRunCatching { studentProfileApiService.withdraw() }
    override suspend fun fetchStudents(): Result<FetchStudentsResponse> =
        suspendRunCatching { studentProfileApiService.fetchStudents() }
}
