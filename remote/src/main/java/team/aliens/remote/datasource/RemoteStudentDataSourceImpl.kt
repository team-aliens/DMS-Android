package team.aliens.remote.datasource

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
import team.aliens.remote.model._common.toDomain
import team.aliens.remote.model.student.toData
import team.aliens.remote.model.student.toDomain
import team.aliens.remote.apiservice.StudentApiService
import team.aliens.remote.util.sendHttpRequest
import javax.inject.Inject

class RemoteStudentDataSourceImpl @Inject constructor(
    private val studentApiService: StudentApiService,
) : RemoteStudentDataSource {

    override suspend fun signUp(
        input: SignUpInput,
    ): AuthenticationOutput {
        return sendHttpRequest {
            studentApiService.signUp(
                request = input.toData(),
            )
        }.toDomain()
    }

    override suspend fun examineStudentNumber(
        input: ExamineStudentNumberInput,
    ): ExamineStudentNumberOutput {
        return sendHttpRequest {
            studentApiService.examineStudentNumber(
                schoolId = input.schoolId,
                grade = input.grade,
                classRoom = input.classRoom,
                number = input.number,
            )
        }.toDomain()
    }

    override suspend fun findId(
        input: FindIdInput,
    ): FindIdOutput {
        return sendHttpRequest {
            studentApiService.findId(
                schoolId = input.schoolId,
                studentName = input.studentName,
                grade = input.grade,
                classRoom = input.classRoom,
                number = input.number,
            )
        }.toDomain()
    }

    override suspend fun resetPassword(
        input: ResetPasswordInput,
    ) {
        return sendHttpRequest {
            studentApiService.resetPassword(
                request = input.toData(),
            )
        }
    }

    override suspend fun checkIdDuplication(
        input: CheckIdDuplicationInput,
    ) {
        return sendHttpRequest {
            studentApiService.checkIdDuplication(
                accountId = input.accountId,
            )
        }
    }

    override suspend fun checkEmailDuplication(
        input: CheckEmailDuplicationInput,
    ) {
        return sendHttpRequest {
            studentApiService.checkEmailDuplication(
                email = input.email,
            )
        }
    }

    override suspend fun fetchMyPage(): FetchMyPageOutput {
        return sendHttpRequest {
            studentApiService.fetchMyPage()
        }.toDomain()
    }

    override suspend fun editProfile(
        input: EditProfileInput,
    ) {
        return sendHttpRequest {
            studentApiService.editProfile(
                request = input.toData(),
            )
        }
    }

    override suspend fun withdraw() {
        return sendHttpRequest {
            studentApiService.withdraw()
        }
    }
}
