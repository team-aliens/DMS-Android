package team.aliens.domain._param.student

/**
 * @author junsuPark
 * A request for password reset, requires all of account information
 * @property accountId user's id, also used at sign in
 * @property name user's real name
 * @property email user's email
 * @property authCode an verification code sent to user's email
 * @property newPassword a password to be changed as
 */
data class ResetPasswordRequest(
    val accountId: String,
    val name: String,
    val email: String,
    val authCode: String,
    val newPassword: String,
)
