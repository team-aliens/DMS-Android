package team.aliens.dms_android.network.datasource

import team.aliens.data.datasource.remote.RemoteStudentDataSource
import team.aliens.dms_android.network.apiservice.StudentApiService
import team.aliens.dms_android.network.model._common.toDomain
import team.aliens.dms_android.network.model.student.toData
import team.aliens.dms_android.network.model.student.toDomain
import team.aliens.dms_android.network.util.sendHttpRequest
import team.aliens.dms_android.domain.model._common.AuthenticationOutput
import team.aliens.domain.model.student.CheckEmailDuplicationInput
import team.aliens.domain.model.student.CheckIdDuplicationInput
import team.aliens.domain.model.student.EditProfileInput
import team.aliens.domain.model.student.ExamineStudentNumberInput
import team.aliens.domain.model.student.ExamineStudentNumberOutput
import team.aliens.domain.model.student.FetchMyPageOutput
import team.aliens.domain.model.student.FindIdInput
import team.aliens.domain.model.student.FindIdOutput
import team.aliens.domain.model.student.ResetPasswordInput
import team.aliens.domain.model.student.SignUpInput
import javax.inject.Inject

class RemoteStudentDataSourceImpl @Inject constructor(
    private val studentApiService: StudentApiService,
) : RemoteStudentDataSource {

    override suspend fun signUp(
        input: SignUpInput,
    ): _root_ide_package_.team.aliens.dms_android.domain.model._common.AuthenticationOutput {
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
