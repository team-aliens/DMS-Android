package team.aliens.domain._model.student

/**
 * An request inputted when editing profile
 * @property profileImageUrl an url expresses user's profile image url
 */
data class EditProfileInput(
    val profileImageUrl: String,
)
