package team.aliens.domain._param.user

/**
 * @author junsuPark
 * A request sent when changing password without verification
 * @property currentPassword a password user using it now
 * @property newPassword a password to be set
 */
data class EditPasswordRequest(
    val currentPassword: String,
    val newPassword: String,
)
