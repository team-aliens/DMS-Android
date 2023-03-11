package team.aliens.domain._param.studyrooms.querystudyroomdetail

import team.aliens.domain._param._common.Sex
import team.aliens.domain._param.studyrooms.querystudyroomdetail.QueryStudyRoomDetailResponse.SeatInformation.SeatStatus.*
import java.util.*

/**
 * @author junsuPark
 * A response returned when querying study room detail
 * [floor] floor of the study room
 * [name] name of the study room
 * [totalAvailableSeat] study room's total available seat
 * [inUseHeadcount] study room's total in use headcount
 * [availableSex] available sex of the study room
 * [availableGrade] available grade of the study room
 * [eastDescription] the description of the east side of the study room
 * [westDescription] the description of the west side of the study room
 * [southDescription] the description of the south side of the study room
 * [northDescription] the description of the north side of the study room
 * [seats] the seats of the study room
 */
data class QueryStudyRoomDetailResponse(
    val floor: Int,
    val name: String,
    val totalAvailableSeat: Int,
    val inUseHeadcount: Int,
    val availableSex: Sex,
    val availableGrade: Int,
    val eastDescription: String,
    val westDescription: String,
    val southDescription: String,
    val northDescription: String,
    val seats: List<SeatInformation>,
) {

    /**
     * @author junsuPark
     * A set of study room seat information
     * [id] the unique id of the seat
     * [widthLocation] X location of the study room chart
     * [heightLocation] Y location of the study room chart
     * [number] the number of the seat
     * [type] the type of the seat
     * []
     */
    data class SeatInformation(
        val id: UUID,
        val widthLocation: Int,
        val heightLocation: Int,
        val number: Int?,
        val type: SeatType?,
        val status: SeatStatus,
        val isMine: Boolean?,
        val student: InUseStudent?,
    ) {

        /**
         * @author junsuPark
         * A set of seat information
         * [id] the unique id of the seat type
         * [name] the name of the seat type
         * [color] the color of the seat type
         */
        data class SeatType(
            val id: UUID,
            val name: String,
            val color: String,
        )

        /**
         * @author junsuPark
         * A enum class of the seat status
         * [AVAILABLE] meaning seat is available
         * [UNAVAILABLE] meaning seat is not available
         * [IN_USE] meaning seat is in-use
         * [EMPTY] meaning seat is empty
         */
        enum class SeatStatus {
            AVAILABLE, UNAVAILABLE, IN_USE, EMPTY,
            ;
        }

        /**
         * @author junsuPark
         * An in-use student information of the seat
         * [id] an unique id of the student
         * [name] the name of the student
         */
        data class InUseStudent(
            val id: UUID,
            val name: String,
        )
    }
}
