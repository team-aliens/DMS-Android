package team.aliens.domain._model.studyroom

import team.aliens.domain._model._common.Sex
import team.aliens.domain._model.studyroom.FetchStudyRoomDetailsOutput.SeatInformation.SeatStatus.*
import java.util.*

/**
 * A response returned when fetching study room details
 * @property floor floor of the study room
 * @property name name of the study room
 * @property totalAvailableSeat study room's total available seat
 * @property inUseHeadcount study room's total in use headcount
 * @property availableSex available sex of the study room
 * @property availableGrade available grade of the study room
 * @property eastDescription the description of the east side of the study room
 * @property westDescription the description of the west side of the study room
 * @property southDescription the description of the south side of the study room
 * @property northDescription the description of the north side of the study room
 * @property seats the seats of the study room
 */
data class FetchStudyRoomDetailsOutput(
    val floor: Int,
    val name: String,
    val startTime: String,
    val endTime: String,
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
     * A set of study room seat information
     * @property id the unique id of the seat
     * @property row X location of the study room chart
     * @property column Y location of the study room chart
     * @property number the number of the seat
     * @property type the type of the seat
     * @property status the status of the seat
     * @property isMine whether the seat is mine
     * @property student information of the student using the seat
     */
    data class SeatInformation(
        val id: UUID,
        val row: Int,
        val column: Int,
        val number: Int?,
        val type: SeatType?,
        val status: SeatStatus,
        val isMine: Boolean?,
        val student: StudentInformation?,
    ) {

        /**
         * A set of seat information
         * @property id the unique id of the seat type
         * @property name the name of the seat type
         * @property color the color of the seat type
         */
        data class SeatType(
            val id: UUID,
            val name: String,
            val color: String,
        )

        /**
         * A enum class of the seat status
         * @property AVAILABLE meaning seat is available
         * @property UNAVAILABLE meaning seat is not available
         * @property IN_USE meaning seat is in-use
         * @property EMPTY meaning seat is empty
         */
        enum class SeatStatus {
            AVAILABLE, UNAVAILABLE, IN_USE, EMPTY,
            ;
        }

        /**
         * An in-use student information of the seat
         * @property id an unique id of the student
         * @property name the name of the student
         */
        data class StudentInformation(
            val id: UUID,
            val name: String,
        )
    }
}
