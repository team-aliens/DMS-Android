package team.aliens.remote.datasource

import team.aliens.data._datasource.remote.RemoteStudentDataSource
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
import team.aliens.remote.apiservice.StudentService
import team.aliens.remote.util.sendHttpRequest
import javax.inject.Inject

class RemoteStudentDataSourceImpl @Inject constructor(
    private val studentService: StudentService,
) : RemoteStudentDataSource {

    override suspend fun signUp(
        input: SignUpInput,
    ): AuthenticationOutput {
        return sendHttpRequest {
            studentService.signUp(
                request = input.toData(),
            )
        }.toDomain()
    }

    override suspend fun examineStudentNumber(
        input: ExamineStudentNumberInput,
    ): ExamineStudentNumberOutput {
        return sendHttpRequest {
            studentService.examineStudentNumber(
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
            studentService.findId(
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
            studentService.resetPassword(
                request = input.toData(),
            )
        }
    }

    override suspend fun checkIdDuplication(
        input: CheckIdDuplicationInput,
    ) {
        return sendHttpRequest {
            studentService.checkIdDuplication(
                accountId = input.accountId,
            )
        }
    }

    override suspend fun checkEmailDuplication(
        input: CheckEmailDuplicationInput,
    ) {
        return sendHttpRequest {
            studentService.checkEmailDuplication(
                email = input.email,
            )
        }
    }

    override suspend fun fetchMyPage(): FetchMyPageOutput {
        return sendHttpRequest {
            studentService.fetchMyPage()
        }.toDomain()
    }

    override suspend fun editProfile(
        input: EditProfileInput,
    ) {
        return sendHttpRequest {
            studentService.editProfile(
                request = input.toData(),
            )
        }
    }

    override suspend fun withdraw() {
        return sendHttpRequest {
            studentService.withdraw()
        }
    }
}
