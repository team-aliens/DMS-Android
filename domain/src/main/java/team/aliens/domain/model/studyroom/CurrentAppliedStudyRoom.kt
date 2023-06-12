package team.aliens.domain.model.studyroom


/**
 * A set of information current applied study room
 * @property floor the floor of the applied study room
 * @property name the name of the applied study room
 */
data class CurrentAppliedStudyRoom(
    val floor: Int,
    val name: String,
)
