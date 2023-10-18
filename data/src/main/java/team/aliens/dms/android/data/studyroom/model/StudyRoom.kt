package team.aliens.dms.android.data.studyroom.model

import team.aliens.dms.android.shared.model.Sex
import java.util.UUID

data class StudyRoom(
    val id: UUID,
    val floor: Int,
    val name: String,
    val availableGrade: Int,
    val availableSex: Sex,
    val inUseHeadcount: Int,
    val totalAvailableSeat: Int,
    val isMine: Boolean,
) {
    data class Details(
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
        val totalWidthSize: Int,
        val totalHeightSize: Int,
        val seats: List<Seat>,
    )

    data class Seat(
        val id: UUID,
        val row: Int,
        val column: Int,
        val number: Int?,
        val type: SeatType?,
        val status: SeatStatus,
        val isMine: Boolean?,
        val student: StudentInformation?,
    ) {
        data class SeatType(
            val id: UUID,
            val name: String,
            val color: String,
        )

        enum class SeatStatus {
            AVAILABLE, UNAVAILABLE, IN_USE, EMPTY,
            ;
        }

        data class StudentInformation(
            val id: UUID,
            val name: String,
        )
    }
}
