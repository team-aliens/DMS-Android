package team.aliens.domain._model.studyroom

import team.aliens.domain._model._common.Sex
import java.util.*

/**
 * A set of study room information, contains study room id, name and etc
 * @property id the unique id of study room
 * @property floor floor of the study room
 * @property name the study room's name
 * @property availableGrade available grade of study room
 * @property availableSex available sex of study room
 * @property inUseHeadcount value of in use headcount of study room
 * @property totalAvailableSeat total available seats of study room
 * @property isMine boolean value of whether current user applied the study room
 */
data class StudyRoom(
    val id: UUID,
    val floor: Int,
    val name: String,
    val availableGrade: Int,
    val availableSex: Sex,
    val inUseHeadcount: Int,
    val totalAvailableSeat: Int,
    val isMine: Boolean,
)
