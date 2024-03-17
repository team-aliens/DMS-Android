package team.aliens.dms.android.data.outing.model

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import team.aliens.dms.android.network.outing.model.FetchCurrentAppliedOutingApplicationResponse

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
        REQUESTED, OUTING, DONE, ;
    }
}

fun FetchCurrentAppliedOutingApplicationResponse.toModel(): CurrentAppliedOutingApplication =
    CurrentAppliedOutingApplication(
        date = this.date,
        type = this.type,
        status = CurrentAppliedOutingApplication.OutingStatus.valueOf(this.status),
        startTime = this.startTime,
        endTime = this.endTime,
        reason = this.reason,
        companionNames = this.companionNames,
    )
