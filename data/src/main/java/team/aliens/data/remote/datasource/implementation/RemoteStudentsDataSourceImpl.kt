package team.aliens.data.remote.datasource.implementation

import team.aliens.data.remote.api.StudentsApi
import team.aliens.data.remote.datasource.declaration.RemoteStudentsDataSource
import team.aliens.data.remote.request.students.EditProfileImageRequest
import team.aliens.data.remote.request.students.ResetPasswordRequest
import team.aliens.data.remote.request.students.SignUpRequest
import team.aliens.data.remote.response.schools.FindIdResponse
import team.aliens.data.remote.response.students.ExamineGradeResponse
import team.aliens.data.util.HttpHandler
import team.aliens.data.util.sendHttpRequest
import java.util.*
import javax.inject.Inject

class RemoteStudentsDataSourceImpl @Inject constructor(
    private val studentsApi: StudentsApi,
) : RemoteStudentsDataSource {

    override suspend fun postUserSignUp(signUpRequest: SignUpRequest) =
        HttpHandler<Unit>()
            .httpRequest { studentsApi.postRegister(signUpRequest) }
            .sendRequest()

    override suspend fun duplicateCheckId(
        accountId: String,
    ) = HttpHandler<Unit>()
        .httpRequest {
            studentsApi.duplicateCheckId(
                accountId
            )
        }.sendRequest()

    override suspend fun duplicateCheckEmail(
        email: String,
    ) = HttpHandler<Unit>()
        .httpRequest {
            studentsApi.duplicateCheckEmail(
                email
            )
        }.sendRequest()

    override suspend fun resetPassword(
        resetPasswordRequest: ResetPasswordRequest,
    ) = HttpHandler<Unit>()
        .httpRequest {
            studentsApi.resetPassword(
                resetPasswordRequest
            )
        }.sendRequest()

    override suspend fun examineGrade(
        schoolId: UUID,
        grade: Int,
        classRoom: Int,
        number: Int,
    ) = HttpHandler<ExamineGradeResponse>()
        .httpRequest {
            studentsApi.examineGrade(
                schoolId = schoolId,
                grade = grade,
                classRoom = classRoom,
                number = number,
            )
        }.sendRequest()

    override suspend fun editProfileImage(
        editProfileImageRequest: EditProfileImageRequest,
    ) {
        sendHttpRequest(
            httpRequest = {
                studentsApi.editProfileImage(
                    editProfileImageRequest = editProfileImageRequest,
                )
            },
        )
    }

    override suspend fun withdraw() {
        sendHttpRequest(
            httpRequest = {
                studentsApi.withdraw()
            }
        )
    }

    override suspend fun findId(
        schoolId: UUID,
        name: String,
        grade: Int,
        classRoom: Int,
        number: Int
    ): FindIdResponse =
        sendHttpRequest(
            httpRequest = {
                studentsApi.findId(
                    schoolId = schoolId,
                    name = name,
                    grade = grade,
                    classRoom = classRoom,
                    number = number
                )
            }
        )
}