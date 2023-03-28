package team.aliens.remote.datasource

import team.aliens.data._datasource.remote.RemoteStudentDataSource
import team.aliens.domain._model._common.AuthenticationOutput
import team.aliens.domain._model.student.*
import java.util.*

class RemoteStudentDataSourceImpl : RemoteStudentDataSource {

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
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun findId(
        schoolId: UUID,
        name: String,
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
