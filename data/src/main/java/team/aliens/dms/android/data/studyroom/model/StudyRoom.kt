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
)
