package team.aliens.domain._param.students.resetpassword

/**
 * @author junsuPark
 * A request for password reset, requires all of account information
 * [accountId] user's id, also used at sign in
 * [name] user's real name
 * [email] user's email
 * [authCode] an verification code sent to user's email
 * [newPassword] a password to be changed as
 */
data class ResetPasswordRequest(
    val accountId: String,
    val name: String,
    val email: String,
    val authCode: String,
    val newPassword: String,
)
