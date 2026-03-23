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
import team.aliens.dms.android.network.student.model.EditPasswordRequest
import team.aliens.dms.android.network.student.model.SignUpRequest
import team.aliens.dms.android.network.student.model.SignUpResponse
import team.aliens.dms.android.network.student.model.extractFeatures
import team.aliens.dms.android.network.student.model.extractTokens
import java.util.UUID
import javax.inject.Inject

internal class StudentRepositoryImpl @Inject constructor(
    private val networkStudentDataSource: NetworkStudentDataSource,
    private val jwtProvider: JwtProvider,
    private val schoolProvider: SchoolProvider,
) : StudentRepository() {

    override suspend fun signUp(param: StudentRepository.SignUpParam): Result<Unit> =
        networkStudentDataSource.signUp(
            request = SignUpRequest(
                schoolVerificationCode = param.schoolVerificationCode,
                schoolVerificationAnswer = param.schoolVerificationAnswer,
                email = param.email,
                emailVerificationCode = param.emailVerificationCode,
                grade = param.grade,
                classRoom = param.classRoom,
                number = param.number,
                accountId = param.accountId,
                password = param.password,
                profileImageUrl = param.profileImageUrl,
            ),
        ).onSuccess {
            jwtProvider.updateTokens(tokens = it.extractTokens())
            schoolProvider.updateFeatures(features = it.extractFeatures())
        }.map {  }

    override suspend fun examineStudentNumber(
        schoolId: UUID,
        grade: Int,
        classroom: Int,
        number: Int,
    ): Result<StudentName> =
        networkStudentDataSource.examineStudentNumber(
            schoolId = schoolId,
            grade = grade,
            classroom = classroom,
            number = number,
        ).map { it.studentName }

    override suspend fun findId(
        schoolId: UUID,
        studentName: String,
        grade: Int,
        classRoom: Int,
        number: Int,
    ): Result<HashedEmail> =
        networkStudentDataSource.findId(
            schoolId = schoolId,
            studentName = studentName,
            grade = grade,
            classRoom = classRoom,
            number = number,
        ).map { it.email }

    override suspend fun editPassword(
        accountId: String,
        studentName: String,
        email: String,
        emailVerificationCode: String,
        newPassword: String,
    ): Result<Unit> =
        networkStudentDataSource.editPassword(
            EditPasswordRequest(
                accountId = accountId,
                studentName = studentName,
                email = email,
                emailVerificationCode = emailVerificationCode,
                newPassword = newPassword,
            ),
        )

    override suspend fun checkIdDuplication(id: String): Result<Unit> =
        networkStudentDataSource.checkIdDuplication(id = id)

    override suspend fun checkEmailDuplication(email: String): Result<Unit> =
        networkStudentDataSource.checkEmailDuplication(email = email)

    override suspend fun fetchMyPage(): Result<MyPage> =
        networkStudentDataSource.fetchMyPage().map { it.toModel() }


    override suspend fun editProfile(profileImageUrl: String): Result<Unit> =
        networkStudentDataSource.editProfile(request = EditProfileRequest(profileImageUrl))


    override suspend fun withdraw(): Result<Unit> {
        return networkStudentDataSource.withdraw().onSuccess {
            jwtProvider.clearCaches()
            schoolProvider.clearCaches()
        }
    }

    override suspend fun fetchStudents(): Result<List<Student>> =
        networkStudentDataSource.fetchStudents().map { it.toModel() }

}
