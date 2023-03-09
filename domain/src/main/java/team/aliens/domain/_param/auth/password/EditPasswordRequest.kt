package team.aliens.domain._param.auth.password

/**
 * @author junsuPark
 * A request used when editing password
 * [currentPassword] a current password
 * [newPassword] a new password to be set
 */
data class EditPasswordRequest(
    val currentPassword: String,
    val newPassword: String,
)
