package team.aliens.dms.android.feature.volunteers

import dagger.hilt.android.lifecycle.HiltViewModel
import team.aliens.dms.android.core.ui.mvi.BaseMviViewModel
import team.aliens.dms.android.core.ui.mvi.Intent
import team.aliens.dms.android.core.ui.mvi.SideEffect
import team.aliens.dms.android.core.ui.mvi.UiState
import team.aliens.dms.android.core.ui.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class VolunteersViewModel @Inject constructor(

) : BaseMviViewModel<VolunteersState, VolunteersIntent, VolunteersSideEffect>(
    initialState = VolunteersState.initial()
) {
}

data class VolunteersState(
    val value: String,
) : UiState() {
    companion object {
        fun initial() = VolunteersState(
            value = "",
        )
    }
}

sealed class VolunteersIntent : Intent() {
    data object Vol
}

sealed class VolunteersSideEffect : SideEffect() {
    data object VolunteersSideEffectn : VolunteersSideEffect()
}
