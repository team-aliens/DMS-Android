package team.aliens.domain.repository

import team.aliens.domain.param.CheckEmailCodeParam
import team.aliens.domain.param.CompareEmailParam
import team.aliens.domain.param.LoginParam
import team.aliens.domain.param.RequestEmailCodeParam

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
}
