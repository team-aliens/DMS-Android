package com.example.auth_data.repository

import com.example.auth_data.remote.datasource.declaration.RemoteStudentsDataSource
import com.example.auth_data.remote.request.students.ResetPasswordRequest
import com.example.auth_data.remote.request.students.SignUpRequest
import com.example.auth_data.remote.response.students.ExamineGradeResponse
import com.example.auth_data.remote.response.students.SignUpResponse
import com.example.auth_domain.entity.ExamineGradeEntity
import com.example.auth_domain.param.ExamineGradeParam
import com.example.auth_domain.param.RegisterParam
import com.example.auth_domain.param.ResetPasswordParam
import com.example.auth_domain.repository.StudentsRepository
import com.example.local_database.datasource.declaration.LocalUserDataSource
import com.example.local_database.param.FeaturesParam
import javax.inject.Inject

class StudentsRepositoryImpl @Inject constructor(
    private val remoteStudentsDataSource: RemoteStudentsDataSource,
    private val localUserDataSource: LocalUserDataSource,
) : StudentsRepository {

    override suspend fun register(registerParam: RegisterParam) {
        val response = remoteStudentsDataSource.postUserSignUp(
            registerParam.toRequest()
        )
        localUserDataSource.setUserVisibleInform(response.features.toEntity())
    }

    override suspend fun duplicateCheckId(accountId: String) =
        remoteStudentsDataSource.duplicateCheckId(accountId)

    override suspend fun duplicateCheckEmail(email: String) =
        remoteStudentsDataSource.duplicateCheckEmail(email)

    override suspend fun resetPassword(resetPasswordParam: ResetPasswordParam) {
        remoteStudentsDataSource.resetPassword(
            resetPasswordParam.toRequest()
        )
    }

    override suspend fun examineGrade(examineGradeParam: ExamineGradeParam): ExamineGradeEntity =
        remoteStudentsDataSource.examineGrade(
            schoolId = examineGradeParam.schoolId,
            grade = examineGradeParam.grade,
            number = examineGradeParam.number,
            classRoom = examineGradeParam.classRoom,
        ).toEntity()

    private fun SignUpResponse.Features.toEntity() =
        FeaturesParam(
            mealService = mealService,
            noticeService = noticeService,
            pointService = pointService,
        )

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
