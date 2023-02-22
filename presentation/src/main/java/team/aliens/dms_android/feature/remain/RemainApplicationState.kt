package team.aliens.dms_android.feature.remain

import team.aliens.dms_android.base.MviState
import java.util.*

data class RemainApplicationState(
    val remainOptionId: UUID,
) : MviState {
    companion object {
        fun initial() = RemainApplicationState(
            remainOptionId = UUID.randomUUID(),
        )
    }
}