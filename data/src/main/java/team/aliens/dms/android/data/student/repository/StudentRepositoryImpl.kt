package team.aliens.dms.android.data.student.repository

import android.util.Log
import team.aliens.dms.android.core.jwt.JwtProvider
import team.aliens.dms.android.core.school.SchoolProvider
import team.aliens.dms.android.data.student.mapper.toModel
import team.aliens.dms.android.data.student.model.HashedEmail
import team.aliens.dms.android.data.student.model.MyPage
import team.aliens.dms.android.data.student.model.StudentName
import team.aliens.dms.android.network.student.datasource.NetworkStudentDataSource
import team.aliens.dms.android.network.student.model.EditProfileRequest
import team.aliens.dms.android.network.student.model.ResetPasswordRequest
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
    ) {
        Log.d("TEST1",profileImageUrl.toString())
        Log.d("TEST1",password)
        val response: SignUpResponse = networkStudentDataSource.signUp(
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
        val tokens = response.extractTokens()
        val features = response.extractFeatures()

        jwtProvider.updateTokens(tokens = tokens)
        schoolProvider.updateFeatures(features = features)
    }

    override suspend fun examineStudentNumber(
        schoolId: UUID,
        grade: Int,
        classroom: Int,
        number: Int,
    ): StudentName = networkStudentDataSource.examineStudentNumber(
        schoolId = schoolId,
        grade = grade,
        classroom = classroom,
        number = number,
    ).studentName

    override suspend fun findId(
        schoolId: UUID,
        studentName: String,
        grade: Int,
        classRoom: Int,
        number: Int,
    ): HashedEmail =
        networkStudentDataSource.findId(
            schoolId = schoolId,
            studentName = studentName,
            grade = grade,
            classRoom = classRoom,
            number = number,
        ).email

    override suspend fun resetPassword(
        accountId: String,
        studentName: String,
        email: String,
        emailVerificationCode: String,
        newPassword: String,
    ) {
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

    override suspend fun checkIdDuplication(id: String) {
        networkStudentDataSource.checkIdDuplication(id = id)
    }

    override suspend fun checkEmailDuplication(email: String) {
        networkStudentDataSource.checkEmailDuplication(email = email)
    }

    override suspend fun fetchMyPage(): MyPage = networkStudentDataSource.fetchMyPage().toModel()

    override suspend fun editProfile(profileImageUrl: String) {
        networkStudentDataSource.editProfile(request = EditProfileRequest(profileImageUrl))
    }

    override suspend fun withdraw() {
        // TODO: remove docs
        /*networkStudentDataSource.withdraw()
        jwtProvider.clearCaches()
        schoolProvider.clearCaches()*/
    }
}
