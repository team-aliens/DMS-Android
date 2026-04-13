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
import team.aliens.dms.android.shared.exception.util.runCatchingCancellable
import java.util.UUID
import javax.inject.Inject

internal class NetworkStudentDataSourceImpl @Inject constructor(
    private val studentAuthApiService: StudentAuthApiService,
    private val studentProfileApiService: StudentProfileApiService,
) : NetworkStudentDataSource() {
    override suspend fun signUp(request: SignUpRequest): Result<SignUpResponse> =
        runCatchingCancellable {
            studentAuthApiService.signUp(request)
        }

    override suspend fun examineStudentNumber(
        schoolId: UUID,
        grade: Int,
        classroom: Int,
        number: Int,
    ): Result<ExamineStudentNumberResponse> = runCatchingCancellable {
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
        runCatchingCancellable {
            studentAuthApiService.findId(
                schoolId = schoolId,
                studentName = studentName,
                grade = grade,
                classRoom = classRoom,
                number = number,
            )
        }

    override suspend fun editPassword(request: EditPasswordRequest): Result<Unit> =
        runCatchingCancellable {
            studentAuthApiService.editPassword(request)
        }

    override suspend fun checkIdDuplication(id: String): Result<Unit> =
        runCatchingCancellable {
            studentAuthApiService.checkIdDuplication(id)
        }

    override suspend fun checkEmailDuplication(email: String): Result<Unit> =
        runCatchingCancellable {
            studentAuthApiService.checkEmailDuplication(email)
        }

    override suspend fun fetchMyPage(): Result<FetchMyPageResponse> =
        runCatchingCancellable {
            studentProfileApiService.fetchMyPage()
        }

    override suspend fun editProfile(request: EditProfileRequest): Result<Unit> =
        runCatchingCancellable {
            studentProfileApiService.editProfile(request)
        }

    override suspend fun withdraw() = runCatchingCancellable { studentProfileApiService.withdraw() }
    override suspend fun fetchStudents(): Result<FetchStudentsResponse> =
        runCatchingCancellable { studentProfileApiService.fetchStudents() }
}
