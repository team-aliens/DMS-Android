package team.aliens.domain._model.student

/**
 * A response, returned while finding user's id
 * @property email user's email, which the user's id was sent
 */
data class FindIdResult(
    val email: String,
)