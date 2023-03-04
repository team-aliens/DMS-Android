package team.aliens.domain.repository

import team.aliens.domain.param.*

interface UserRepository {

    suspend fun userSignIn(
        loginParam: LoginParam,
    )

    suspend fun autoSignIn()

    suspend fun requestEmailCode(
        requestEmailCodeParam: RequestEmailCodeParam,
    )

    suspend fun checkEmailCode(
        checkEmailCodeParam: CheckEmailCodeParam,
    )

    suspend fun reissueToken(
        refreshToken: String,
    )

    suspend fun compareEmail(
        compareEmailParam: CompareEmailParam,
    )

    suspend fun checkId(
        accountId: String,
    )

    suspend fun editPassword(
        editPasswordParam: EditPasswordParam,
    )

    suspend fun comparePassword(
        password: String,
    )

    suspend fun signOut()

    suspend fun fetchAutoSignInOption(): Boolean
}
