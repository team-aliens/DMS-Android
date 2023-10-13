package team.aliens.dms.android.domain.model.studyroom

/**
 * A response returned when fetching current applied study room
 * @property floor the floor of the applied study room
 * @property name the name of the applied study room
 */
data class FetchCurrentAppliedStudyRoomOutput(
    val floor: Int,
    val name: String,
)

fun FetchCurrentAppliedStudyRoomOutput.toModel(): CurrentAppliedStudyRoom {
    return CurrentAppliedStudyRoom(
        floor = this.floor,
        name = this.name,
    )
}
