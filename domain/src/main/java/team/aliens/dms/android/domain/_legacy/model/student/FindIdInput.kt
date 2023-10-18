package team.aliens.dms.android.domain._legacy.model.student

import java.util.UUID

data class FindIdInput(
    val schoolId: UUID,
    val studentName: String,
    val grade: Int,
    val classRoom: Int,
    val number: Int,
)
