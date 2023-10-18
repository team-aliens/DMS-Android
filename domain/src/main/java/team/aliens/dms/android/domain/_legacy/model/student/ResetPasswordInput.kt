package team.aliens.dms.android.domain._legacy.model.student

/**
 * A request for password reset, requires all of account information
 * @property accountId user's id, also used at sign in
 * @property studentName user's real name
 * @property email user's email
 * @property emailVerificationCode an verification code sent to user's email
 * @property newPassword a password to be changed as
 */
data class ResetPasswordInput(
    val accountId: String,
    val studentName: String,
    val email: String,
    val emailVerificationCode: String,
    val newPassword: String,
)
