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
        val id: UUID,
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
        val type: Type?,
        val status: Status,
        val isMine: Boolean,
        val student: Student?,
    ) {
        data class Type(
            val id: UUID,
            val name: String,
            val color: String,
        )

        enum class Status {
            AVAILABLE, UNAVAILABLE, IN_USE, EMPTY,
            ;
        }

        data class Student(
            val id: UUID,
            val name: String,
        )
    }
}

fun StudyRoom.Details.toStudyRoom(
    isMine: Boolean = false,
): StudyRoom = StudyRoom(
    id = this.id,
    floor = this.floor,
    name = this.name,
    availableGrade = this.availableGrade,
    availableSex = this.availableSex,
    inUseHeadcount = this.inUseHeadcount,
    totalAvailableSeat = this.totalAvailableSeat,
    isMine = isMine,
)
