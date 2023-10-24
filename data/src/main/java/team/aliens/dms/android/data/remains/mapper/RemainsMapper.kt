package team.aliens.dms.android.data.remains.mapper

import team.aliens.dms.android.data.remains.model.AppliedRemainsOption
import team.aliens.dms.android.network.remains.model.FetchAppliedRemainsOptionResponse

fun FetchAppliedRemainsOptionResponse.toModel(): AppliedRemainsOption = AppliedRemainsOption(
    remainsOptionId = this.remainsOptionId,
    title = this.title,
)
