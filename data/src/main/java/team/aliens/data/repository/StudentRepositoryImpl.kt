package team.aliens.data.repository

import team.aliens.data.datasource.remote.RemoteStudentDataSource
import team.aliens.domain._model._common.AuthenticationOutput
import team.aliens.domain._model.student.CheckEmailDuplicationInput
import team.aliens.domain._model.student.CheckIdDuplicationInput
import team.aliens.domain._model.student.EditProfileInput
import team.aliens.domain._model.student.ExamineStudentNumberInput
import team.aliens.domain._model.student.ExamineStudentNumberOutput
import team.aliens.domain._model.student.FetchMyPageOutput
import team.aliens.domain._model.student.FindIdInput
import team.aliens.domain._model.student.FindIdOutput
import team.aliens.domain._model.student.ResetPasswordInput
import team.aliens.domain._model.student.SignUpInput
import team.aliens.domain._repository.StudentRepository
import javax.inject.Inject

class StudentRepositoryImpl @Inject constructor(
    private val remoteStudentDataSource: RemoteStudentDataSource,
) : StudentRepository {

    override suspend fun signUp(
        input: SignUpInput,
    ): AuthenticationOutput {
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
