package team.aliens.dms.android.data.remains.mapper

import team.aliens.dms.android.data.remains.model.AppliedRemainsOption
import team.aliens.dms.android.data.remains.model.RemainsApplicationTime
import team.aliens.dms.android.data.remains.model.RemainsOption
import team.aliens.dms.android.network.remains.model.FetchAppliedRemainsOptionResponse
import team.aliens.dms.android.network.remains.model.FetchRemainsApplicationTimeResponse
import team.aliens.dms.android.network.remains.model.FetchRemainsOptionsResponse

internal fun FetchAppliedRemainsOptionResponse.toModel(): AppliedRemainsOption =
    AppliedRemainsOption(
        id = this.remainsOptionId,
        title = this.title,
    )

internal fun FetchRemainsApplicationTimeResponse.toModel(): RemainsApplicationTime =
    RemainsApplicationTime(
        startDayOfWeek = this.startDayOfWeek,
        startTime = this.startTime,
        endDayOfWeek = this.endDayOfWeek,
        endTime = this.endTime,
    )

internal fun FetchRemainsOptionsResponse.toModel(): List<RemainsOption> =
    this.remainsOptionResponse.map(FetchRemainsOptionsResponse.RemainsOptionResponse::toModel)

private fun FetchRemainsOptionsResponse.RemainsOptionResponse.toModel(): RemainsOption =
    RemainsOption(
        id = this.id,
        title = this.title,
        description = this.description,
        applied = this.applied,
    )
