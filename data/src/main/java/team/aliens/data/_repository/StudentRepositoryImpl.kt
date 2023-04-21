package team.aliens.data._repository

import team.aliens.data._datasource.remote.RemoteStudentDataSource
import team.aliens.domain._model._common.AuthenticationOutput
import team.aliens.domain._model.student.*
import team.aliens.domain._repository.StudentRepository
import java.util.*
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
        schoolId: UUID,
        grade: Int,
        classRoom: Int,
        number: Int,
    ): ExamineStudentNumberOutput {
        return remoteStudentDataSource.examineStudentNumber(
            schoolId = schoolId,
            grade = grade,
            classRoom = classRoom,
            number = number,
        )
    }

    override suspend fun findId(
        schoolId: UUID,
        studentName: String,
        grade: Int,
        classRoom: Int,
        number: Int,
    ): FindIdOutput {
        return remoteStudentDataSource.findId(
            schoolId = schoolId,
            studentName = studentName,
            grade = grade,
            classRoom = classRoom,
            number = number,
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
