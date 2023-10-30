package team.aliens.dms.android.data.remains.mapper

import team.aliens.dms.android.data.remains.model.AppliedRemainsOption
import team.aliens.dms.android.data.remains.model.RemainsApplicationTime
import team.aliens.dms.android.network.remains.model.FetchAppliedRemainsOptionResponse
import team.aliens.dms.android.network.remains.model.FetchRemainsApplicationTimeResponse

internal fun FetchAppliedRemainsOptionResponse.toModel(): AppliedRemainsOption =
    AppliedRemainsOption(
        remainsOptionId = this.remainsOptionId,
        title = this.title,
    )

internal fun FetchRemainsApplicationTimeResponse.toModel(): RemainsApplicationTime =
    RemainsApplicationTime(
        startDayOfWeek = startDayOfWeek,
        startTime = startTime,
        endDayOfWeek = endDayOfWeek,
        endTime = endTime,
    )
