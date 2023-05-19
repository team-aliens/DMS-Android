package team.aliens.domain.model.student

/**
 * A response, returned when finding user's id
 * @property email user's email, which the user's id was sent
 */
data class FindIdOutput(
    val email: String,
)