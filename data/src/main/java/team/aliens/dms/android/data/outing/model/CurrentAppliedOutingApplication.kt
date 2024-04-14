package team.aliens.dms.android.data.outing.model

import team.aliens.dms.android.network.outing.model.FetchCurrentAppliedOutingApplicationResponse
import java.util.UUID

data class CurrentAppliedOutingApplication(
    val id: UUID,
    val date: String,
    val type: String,
    val status: OutingStatus,
    val startTime: String,
    val endTime: String,
    val reason: String?,
    val companionNames: List<String>,
) {
    // TODO
    enum class OutingStatus {
        REQUESTED,
        OUTING,
        DONE,
        APPROVED,
        ;
    }
}

fun FetchCurrentAppliedOutingApplicationResponse.toModel(): CurrentAppliedOutingApplication =
    CurrentAppliedOutingApplication(
        id = UUID.fromString(this.id),
        date = this.date,
        type = this.type,
        status = CurrentAppliedOutingApplication.OutingStatus.valueOf(this.status),
        startTime = this.startTime,
        endTime = this.endTime,
        reason = this.reason,
        companionNames = this.companionNames,
    )
