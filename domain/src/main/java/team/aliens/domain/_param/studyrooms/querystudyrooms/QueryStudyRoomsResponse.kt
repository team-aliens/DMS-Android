package team.aliens.domain._param.studyrooms.querystudyrooms

import team.aliens.domain._param._common.Sex
import java.util.*

/**
 * @author junsuPark
 * A response returned when querying study rooms
 * [studyRooms] list of study rooms
 */
data class QueryStudyRoomsResponse(
    val studyRooms: List<StudyRoomInformation>,
) {

    /**
     * @author junsuPark
     * A set of study room information
     * [id] unique id of the school
     * [floor] the floor of the study room
     * [name] the name of the study room
     * [availableGrade] the available grade of the study room
     * [availableSex] the available sex of the study room
     * [inUseHeadcount] the in-use headcount of the study room
     * [totalAvailableSeat] the total available seat of the study room
     * [isMine] the boolean value of whether 'I' applied at this study room
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
