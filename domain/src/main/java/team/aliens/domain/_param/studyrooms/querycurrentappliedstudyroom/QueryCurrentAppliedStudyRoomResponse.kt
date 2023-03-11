package team.aliens.domain._param.studyrooms.querycurrentappliedstudyroom

/**
 * @author junsuPark
 * A response returned when querying
 * [floor] the floor of the applied study room
 * [name] the name of the applied study room
 */
data class QueryCurrentAppliedStudyRoomResponse(
    val floor: Int,
    val name: String,
)
