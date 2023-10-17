package team.aliens.dms.android.data.auth.repository

abstract class AuthRepository {

    abstract suspend fun signIn(
        accountId: String,
        password: String,
    )

    abstract suspend fun sendEmailVerificationCode(
        email: String,
        type: Email
    )
}
