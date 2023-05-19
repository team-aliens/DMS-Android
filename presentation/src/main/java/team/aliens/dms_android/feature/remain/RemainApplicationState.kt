package team.aliens.dms_android.feature.remain

import team.aliens.dms_android._base.MviState
import java.util.*

data class RemainApplicationState(
    val remainOptionId: UUID,
) : MviState {
    companion object {
        fun getDefaultInstance() = RemainApplicationState(
            remainOptionId = UUID.randomUUID(),
        )
    }
}