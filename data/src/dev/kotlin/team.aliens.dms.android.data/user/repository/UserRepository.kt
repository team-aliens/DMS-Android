package team.aliens.dms.android.data.user.repository

abstract class UserRepository {

    abstract suspend fun editPassword(
        currentPassword: String,
        newPassword: String,
    )

    abstract suspend fun comparePassword(password: String)
}
