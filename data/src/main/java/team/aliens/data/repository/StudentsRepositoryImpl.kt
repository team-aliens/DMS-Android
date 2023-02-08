package team.aliens.data.repository

import com.example.data.remote.request.students.ResetPasswordRequest
import com.example.data.remote.request.students.SignUpRequest
import com.example.data.remote.response.students.ExamineGradeResponse
import com.example.data.remote.datasource.declaration.RemoteStudentsDataSource
import com.example.data.util.OfflineCacheUtil
import com.example.domain.entity.user.ExamineGradeEntity
import com.example.domain.param.ExamineGradeParam
import com.example.domain.param.RegisterParam
import com.example.domain.param.ResetPasswordParam
import com.example.domain.repository.StudentsRepository
import com.example.local_database.datasource.declaration.LocalUserDataSource
import kotlinx.coroutines.flow.Flow
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
        remoteStudentsDataSource.resetPassword(
            resetPasswordParam.toRequest()
        )
    }

    override suspend fun examineGrade(examineGradeParam: ExamineGradeParam): Flow<ExamineGradeEntity> =
        OfflineCacheUtil<ExamineGradeEntity>()
            .remoteData {
                remoteStudentsDataSource.examineGrade(
                    schoolId = examineGradeParam.schoolId,
                    grade = examineGradeParam.grade,
                    classRoom = examineGradeParam.classRoom,
                    number = examineGradeParam.number,
                ).toEntity()
            }.createRemoteFlow()
    
    private fun ResetPasswordParam.toRequest() =
        ResetPasswordRequest(
            accountId = accountId,
            authCode = authCode,
            email = email,
            name = name,
            newPassword = newPassword
        )

    private fun RegisterParam.toRequest() =
        SignUpRequest(
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

    private fun ExamineGradeResponse.toEntity() =
        ExamineGradeEntity(
            name = name
        )
}
