package team.aliens.data._repository

import team.aliens.domain._model._common.AuthenticationOutput
import team.aliens.domain._model.student.*
import team.aliens.domain._repository.StudentRepository
import java.util.*

class StudentRepositoryImpl(
    // private val remoteStudentDataSource: RemoteStudentDataSource,
) : StudentRepository {

    override suspend fun signUp(
        input: SignUpInput,
    ): AuthenticationOutput {
        TODO("Not yet implemented")
    }

    override suspend fun examineStudentNumber(
        schoolId: UUID,
        grade: Int,
        classRoom: Int,
        number: Int,
    ): ExamineStudentNumberOutput {
        TODO("Not yet implemented")
    }

    override suspend fun findId(
        schoolId: UUID,
        studentName: String,
        grade: Int,
        classRoom: Int,
        number: Int,
    ): FindIdOutput {
        TODO("Not yet implemented")
    }

    override suspend fun resetPassword(
        input: ResetPasswordInput,
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun checkIdDuplication(
        accountId: String,
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun checkEmailDuplication(
        email: String,
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun fetchMyPage(): FetchMyPageOutput {
        TODO("Not yet implemented")
    }

    override suspend fun editProfile(
        input: EditProfileInput,
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun withdraw() {
        TODO("Not yet implemented")
    }
}
