package team.aliens.remote.datasource

import team.aliens.data._datasource.remote.RemoteStudentDataSource
import team.aliens.domain._model._common.AuthenticationOutput
import team.aliens.domain._model.student.CheckEmailDuplicationInput
import team.aliens.domain._model.student.EditProfileInput
import team.aliens.domain._model.student.ExamineStudentNumberOutput
import team.aliens.domain._model.student.FetchMyPageOutput
import team.aliens.domain._model.student.FindIdOutput
import team.aliens.domain._model.student.ResetPasswordInput
import team.aliens.domain._model.student.SignUpInput
import team.aliens.remote.model._common.toDomain
import team.aliens.remote.model.student.toData
import team.aliens.remote.model.student.toDomain
import team.aliens.remote.service.StudentService
import team.aliens.remote.util.sendHttpRequest
import java.util.UUID
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
        schoolId: UUID,
        grade: Int,
        classRoom: Int,
        number: Int,
    ): ExamineStudentNumberOutput {
        return sendHttpRequest {
            studentService.examineStudentNumber(
                schoolId = schoolId,
                grade = grade,
                classRoom = classRoom,
                number = number,
            )
        }.toDomain()
    }

    override suspend fun findId(
        schoolId: UUID,
        studentName: String,
        grade: Int,
        classRoom: Int,
        number: Int,
    ): FindIdOutput {
        return sendHttpRequest {
            studentService.findId(
                schoolId = schoolId,
                studentName = studentName,
                grade = grade,
                classRoom = classRoom,
                number = number,
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
        accountId: String,
    ) {
        return sendHttpRequest {
            studentService.checkIdDuplication(
                accountId = accountId,
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
