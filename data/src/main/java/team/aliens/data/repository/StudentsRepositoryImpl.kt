package team.aliens.data.repository

import kotlinx.coroutines.flow.Flow
import team.aliens.data.remote.datasource.declaration.RemoteStudentsDataSource
import team.aliens.data.remote.request.students.ResetPasswordRequest
import team.aliens.data.remote.request.students.SignUpRequest
import team.aliens.data.remote.response.students.ExamineGradeResponse
import team.aliens.data.util.OfflineCacheUtil
import team.aliens.domain.entity.user.ExamineGradeEntity
import team.aliens.domain.param.ExamineGradeParam
import team.aliens.domain.param.RegisterParam
import team.aliens.domain.param.ResetPasswordParam
import team.aliens.domain.repository.StudentsRepository
import team.aliens.local_database.datasource.declaration.LocalUserDataSource
import javax.inject.Inject

class StudentsRepositoryImpl @Inject constructor(
    private val remoteStudentsDataSource: RemoteStudentsDataSource,
    private val localUserDataSource: LocalUserDataSource,
) : StudentsRepository {

    override suspend fun register(registerParam: RegisterParam) =
        remoteStudentsDataSource.postUserSignUp(registerParam.toRequest())

    override suspend fun duplicateCheckId(accountId: String) =
        remoteStudentsDataSource.duplicateCheckId(accountId)

    override suspend fun duplicateCheckEmail(email: String) =
        remoteStudentsDataSource.duplicateCheckEmail(email)

    override suspend fun resetPassword(resetPasswordParam: ResetPasswordParam) {
        remoteStudentsDataSource.resetPassword(resetPasswordParam.toRequest())
    }

    override suspend fun examineGrade(examineGradeParam: ExamineGradeParam): Flow<ExamineGradeEntity> =
        OfflineCacheUtil<ExamineGradeEntity>().remoteData {
            remoteStudentsDataSource.examineGrade(
                schoolId = examineGradeParam.schoolId,
                grade = examineGradeParam.grade,
                classRoom = examineGradeParam.classRoom,
                number = examineGradeParam.number,
            ).toEntity()
        }.createRemoteFlow()

    private fun ResetPasswordParam.toRequest() = ResetPasswordRequest(accountId = accountId,
        authCode = authCode,
        email = email,
        name = name,
        newPassword = newPassword)

    private fun RegisterParam.toRequest() = SignUpRequest(
        schoolCode = schoolCode,
        schoolAnswer = schoolAnswer,
        email = email,
        authCode = authCode,
        grade = grade,
        classRoom = classRoom,
        number = number,
        accountId = accountId,
        password = password,
        profileImageUrl = profileImageUrl,
    )

    private fun ExamineGradeResponse.toEntity() = ExamineGradeEntity(name = name)
}
