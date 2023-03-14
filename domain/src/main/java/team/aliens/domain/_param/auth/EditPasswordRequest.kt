package team.aliens.domain._param.auth

/**
 * @author junsuPark
 * A request used when editing password
 * @property currentPassword a current password
 * @property newPassword a new password to be set
 */
data class EditPasswordRequest(
    val currentPassword: String,
    val newPassword: String,
)
