package team.aliens.domain._model.user

/**
 * A request sent when editing password, requires authentication
 * @property currentPassword a password user using it now
 * @property newPassword a password to be set
 */
data class EditPasswordInput(
    val currentPassword: String,
    val newPassword: String,
)
