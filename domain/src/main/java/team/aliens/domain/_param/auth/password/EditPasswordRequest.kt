package team.aliens.domain._param.auth.password

/**
 * @author junsuPark
 * [currentPassword] a current password
 * [newPassword] a new password to be set
 */
data class EditPasswordRequest(
    val currentPassword: String,
    val newPassword: String,
)
