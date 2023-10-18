package team.aliens.dms.android.domain._legacy.model.studyroom

import team.aliens.dms.android.domain.model._common.Sex
import java.util.UUID

/**
 * A response returned when fetching study rooms
 * @property studyRooms list of study rooms
 */
data class FetchStudyRoomsOutput(
    val studyRooms: List<StudyRoomInformation>,
) {

    /**
     * A set of study room information
     * @property id unique id of the school
     * @property floor the floor of the study room
     * @property name the name of the study room
     * @property availableGrade the available grade of the study room
     * @property availableSex the available sex of the study room
     * @property inUseHeadcount the in-use headcount of the study room
     * @property totalAvailableSeat the total available seat of the study room
     * @property isMine the boolean value of whether 'I' applied at this study room
     */
    data class StudyRoomInformation(
        val id: UUID,
        val floor: Int,
        val name: String,
        val availableGrade: Int,
        val availableSex: Sex,
        val inUseHeadcount: Int,
        val totalAvailableSeat: Int,
        val isMine: Boolean,
    )
}