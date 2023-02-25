package team.aliens.domain.repository

import kotlinx.coroutines.flow.Flow
import team.aliens.domain.entity.user.ExamineGradeEntity
import team.aliens.domain.param.ExamineGradeParam
import team.aliens.domain.param.RegisterParam
import team.aliens.domain.param.ResetPasswordParam

interface StudentsRepository {

    suspend fun register(
        registerParam: RegisterParam,
    )

    suspend fun duplicateCheckId(
        accountId: String,
    )

    suspend fun duplicateCheckEmail(
        email: String,
    )

    suspend fun resetPassword(
        resetPasswordParam: ResetPasswordParam,
    )

    suspend fun examineGrade(
        examineGradeParam: ExamineGradeParam,
    ): Flow<ExamineGradeEntity>

    suspend fun editProfileImage(
        profileImageUrl: String,
    )
}