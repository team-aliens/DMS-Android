package team.aliens.dms_android.feature.remain

import java.util.UUID
import team.aliens.dms_android.base.UiEvent

sealed class RemainsApplicationUiEvent : UiEvent {
    object FetchAvailableRemainsTimeUi : RemainsApplicationUiEvent()
    object FetchUiRemainsOptions : RemainsApplicationUiEvent()
    object FetchCurrentAppliedRemainsOptionUi : RemainsApplicationUiEvent()
    class UpdateUiRemainsOption(
        val remainsOptionId: UUID,
    ) : RemainsApplicationUiEvent()
}