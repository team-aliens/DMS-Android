package team.aliens.dms_android.feature.remain

import java.util.UUID
import team.aliens.dms_android.base.UiEvent

sealed class RemainsApplicationUiEvent : UiEvent {
    object FetchAvailableRemainsTime : RemainsApplicationUiEvent()
    object FetchRemainsOptions : RemainsApplicationUiEvent()
    object FetchCurrentAppliedRemainsOption : RemainsApplicationUiEvent()
    object UpdateUiRemainsOption : RemainsApplicationUiEvent()
    class SetRemainsOptionItemId(
        val remainsOptionId: UUID,
    ) : RemainsApplicationUiEvent()
}