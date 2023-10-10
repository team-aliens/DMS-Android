package team.aliens.dms_android.data.repository

import team.aliens.dms_android.data.datasource.remote.RemoteStudentDataSource
import team.aliens.dms_android.domain.model.student.CheckEmailDuplicationInput
import team.aliens.dms_android.domain.model.student.CheckIdDuplicationInput
import team.aliens.dms_android.domain.model.student.EditProfileInput
import team.aliens.dms_android.domain.model.student.ExamineStudentNumberInput
import team.aliens.dms_android.domain.model.student.ExamineStudentNumberOutput
import team.aliens.dms_android.domain.model.student.FetchMyPageOutput
import team.aliens.dms_android.domain.model.student.FindIdInput
import team.aliens.dms_android.domain.model.student.FindIdOutput
import team.aliens.dms_android.domain.model.student.ResetPasswordInput
import team.aliens.dms_android.domain.model.student.SignUpInput
import team.aliens.dms_android.domain.repository.StudentRepository
import javax.inject.Inject

class StudentRepositoryImpl @Inject constructor(
    private val remoteStudentDataSource: RemoteStudentDataSource,
) : StudentRepository {

    override suspend fun signUp(
        input: SignUpInput,
    ): team.aliens.dms_android.domain.model._common.AuthenticationOutput {
        return remoteStudentDataSource.signUp(
            input = input,
        )
    }

    override suspend fun examineStudentNumber(
        input: ExamineStudentNumberInput,
    ): ExamineStudentNumberOutput {
        return remoteStudentDataSource.examineStudentNumber(
            input = input,
        )
    }

    override suspend fun findId(
        input: FindIdInput,
    ): FindIdOutput {
        return remoteStudentDataSource.findId(
            input = input,
        )
    }

    override suspend fun resetPassword(
        input: ResetPasswordInput,
    ) {
        return remoteStudentDataSource.resetPassword(
            input = input,
        )
    }

    override suspend fun checkIdDuplication(
        input: CheckIdDuplicationInput,
    ) {
        return remoteStudentDataSource.checkIdDuplication(
            input = input,
        )
    }

    override suspend fun checkEmailDuplication(
        input: CheckEmailDuplicationInput,
    ) {
        return remoteStudentDataSource.checkEmailDuplication(
            input = input,
        )
    }

    override suspend fun fetchMyPage(): FetchMyPageOutput {
        return remoteStudentDataSource.fetchMyPage()
    }

    override suspend fun editProfile(
        input: EditProfileInput,
    ) {
        return remoteStudentDataSource.editProfile(
            input = input,
        )
    }

    override suspend fun withdraw() {
        return remoteStudentDataSource.withdraw()
    }
}
