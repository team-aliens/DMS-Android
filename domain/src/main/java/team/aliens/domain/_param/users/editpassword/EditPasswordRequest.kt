package team.aliens.domain._param.users.editpassword

/**
 * @author junsuPark
 * A request sent when changing password without verification
 * [currentPassword] a password user using it now
 * [newPassword] a password to be set
 */
data class EditPasswordRequest(
    val currentPassword: String,
    val newPassword: String,
)
