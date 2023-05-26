package team.aliens.dms_android.feature.remain

import java.util.UUID
import team.aliens.dms_android.base.UiEvent

sealed class RemainsApplicationEvent : UiEvent {
    object FetchAvailableRemainsTime : RemainsApplicationEvent()
    object FetchRemainsOptions : RemainsApplicationEvent()
    object FetchCurrentAppliedRemainsOption : RemainsApplicationEvent()
    class UpdateRemainsOption(
        val remainsOptionId: UUID,
    ) : RemainsApplicationEvent()
}