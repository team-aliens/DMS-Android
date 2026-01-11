package team.aliens.dms.android.data.student.repository

import team.aliens.dms.android.core.jwt.JwtProvider
import team.aliens.dms.android.core.school.SchoolProvider
import team.aliens.dms.android.data.student.mapper.toModel
import team.aliens.dms.android.data.student.model.HashedEmail
import team.aliens.dms.android.data.student.model.MyPage
import team.aliens.dms.android.data.student.model.Student
import team.aliens.dms.android.data.student.model.StudentName
import team.aliens.dms.android.data.student.model.toModel
import team.aliens.dms.android.network.student.datasource.NetworkStudentDataSource
import team.aliens.dms.android.network.student.model.EditProfileRequest
import team.aliens.dms.android.network.student.model.ExamineStudentNumberResponse
import team.aliens.dms.android.network.student.model.FindIdResponse
import team.aliens.dms.android.network.student.model.ResetPasswordRequest
import team.aliens.dms.android.network.student.model.SignUpRequest
import team.aliens.dms.android.network.student.model.SignUpResponse
import team.aliens.dms.android.network.student.model.extractFeatures
import team.aliens.dms.android.network.student.model.extractTokens
import java.util.UUID
import javax.inject.Inject
import kotlin.map

internal class StudentRepositoryImpl @Inject constructor(
    private val networkStudentDataSource: NetworkStudentDataSource,
    private val jwtProvider: JwtProvider,
    private val schoolProvider: SchoolProvider,
) : StudentRepository() {

    override suspend fun signUp(
        schoolVerificationCode: String,
        schoolVerificationAnswer: String,
        email: String,
        emailVerificationCode: String,
        grade: Int,
        classRoom: Int,
        number: Int,
        accountId: String,
        password: String,
        profileImageUrl: String?,
    ): Result<Unit> = runCatching {
        val response: Result<SignUpResponse> = networkStudentDataSource.signUp(
            request = SignUpRequest(
                schoolVerificationCode = schoolVerificationCode,
                schoolVerificationAnswer = schoolVerificationAnswer,
                email = email,
                emailVerificationCode = emailVerificationCode,
                grade = grade,
                classRoom = classRoom,
                number = number,
                accountId = accountId,
                password = password,
                profileImageUrl = profileImageUrl,
            ),
        )
        response.onSuccess {
            jwtProvider.updateTokens(tokens = it.extractTokens())
            schoolProvider.updateFeatures(features = it.extractFeatures())
        }
    }

    override suspend fun examineStudentNumber(
        schoolId: UUID,
        grade: Int,
        classroom: Int,
        number: Int,
    ): Result<ExamineStudentNumberResponse> =
        networkStudentDataSource.examineStudentNumber(
            schoolId = schoolId,
            grade = grade,
            classroom = classroom,
            number = number,
        )
// TODO :: usecase 사용
//        networkStudentDataSource.examineStudentNumber(
//            schoolId = schoolId,
//            grade = grade,
//            classroom = classroom,
//            number = number,
//        ).studentName


    override suspend fun findId(
        schoolId: UUID,
        studentName: String,
        grade: Int,
        classRoom: Int,
        number: Int,
    ): Result<FindIdResponse> =
        networkStudentDataSource.findId(
            schoolId = schoolId,
            studentName = studentName,
            grade = grade,
            classRoom = classRoom,
            number = number,
        )

//    runCatching {
//        networkStudentDataSource.findId(
//            schoolId = schoolId,
//            studentName = studentName,
//            grade = grade,
//            classRoom = classRoom,
//            number = number,
//        ).email
//    }

    override suspend fun resetPassword(
        accountId: String,
        studentName: String,
        email: String,
        emailVerificationCode: String,
        newPassword: String,
    ): Result<Unit> = runCatching {
        networkStudentDataSource.resetPassword(
            ResetPasswordRequest(
                accountId = accountId,
                studentName = studentName,
                email = email,
                emailVerificationCode = emailVerificationCode,
                newPassword = newPassword,
            ),
        )
    }

    override suspend fun checkIdDuplication(id: String): Result<Unit> =
        networkStudentDataSource.checkIdDuplication(id = id)

    override suspend fun checkEmailDuplication(email: String): Result<Unit> =
        networkStudentDataSource.checkEmailDuplication(email = email)

    override suspend fun fetchMyPage(): Result<MyPage> =
        networkStudentDataSource.fetchMyPage().map { it.toModel() }


    override suspend fun editProfile(profileImageUrl: String): Result<Unit> =
        networkStudentDataSource.editProfile(request = EditProfileRequest(profileImageUrl))


    override suspend fun withdraw(): Result<Unit> {
        jwtProvider.clearCaches()
        schoolProvider.clearCaches()
        return networkStudentDataSource.withdraw()
    }

    override suspend fun fetchStudents(): Result<List<Student>> =
        networkStudentDataSource.fetchStudents().map { it.toModel() }

}
