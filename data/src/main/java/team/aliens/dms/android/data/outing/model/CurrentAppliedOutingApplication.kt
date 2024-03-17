package team.aliens.dms.android.data.outing.model

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime

data class CurrentAppliedOutingApplication(
    val date: LocalDate,
    val type: String,
    val status: OutingStatus,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val reason: String?,
    val companionNames: List<String>,
) {
    // TODO
    enum class OutingStatus {
        REQUESTED,
        OUTING,
        DONE, ;
    }
}


