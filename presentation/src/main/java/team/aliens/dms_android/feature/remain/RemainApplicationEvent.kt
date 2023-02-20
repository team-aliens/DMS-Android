package team.aliens.dms_android.feature.remain

import team.aliens.dms_android.base.MviEvent
import java.util.*

sealed class RemainApplicationEvent : MviEvent {
    data class SetRemainOptionId(val remainOptionId: UUID) : RemainApplicationEvent()
}