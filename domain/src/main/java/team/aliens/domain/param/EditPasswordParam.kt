package team.aliens.domain.param

data class EditPasswordParam(
    val currentPassword: String,
    val newPassword: String,
)