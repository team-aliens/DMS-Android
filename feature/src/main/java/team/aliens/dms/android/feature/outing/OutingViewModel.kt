package team.aliens.dms.android.feature.outing

import dagger.hilt.android.lifecycle.HiltViewModel
import team.aliens.dms.android.core.ui.mvi.BaseMviViewModel
import team.aliens.dms.android.core.ui.mvi.Intent
import team.aliens.dms.android.core.ui.mvi.SideEffect
import team.aliens.dms.android.core.ui.mvi.UiState
import team.aliens.dms.android.data.outing.model.CurrentAppliedOutingApplication
import team.aliens.dms.android.data.outing.model.OutingApplicationTime
import team.aliens.dms.android.data.outing.repository.OutingRepository
import javax.inject.Inject

@HiltViewModel
class OutingViewModel @Inject constructor(
    private val outingRepository: OutingRepository,
) : BaseMviViewModel<OutingUiState, OutingIntent, OutingSideEffect>(
    initialState = OutingUiState.initial(),
) {
    override fun processIntent(intent: OutingIntent) {

    }
}

data class OutingUiState(
    // val outingApplicationTime: List<OutingApplicationTime>?,
    val currentAppliedOutingApplication: CurrentAppliedOutingApplication?,
) : UiState() {
    companion object {
        fun initial() = OutingUiState(
            currentAppliedOutingApplication  = null,
        )
    }
}

sealed class OutingIntent : Intent()

sealed class OutingSideEffect : SideEffect()
