package team.aliens.dms.android.feature.volunteers

import androidx.compose.runtime.Immutable
import dagger.hilt.android.lifecycle.HiltViewModel
import team.aliens.dms.android.core.jwt.AccessToken
import team.aliens.dms.android.core.jwt.RefreshToken
import team.aliens.dms.android.core.jwt.datastore.JwtDataStoreDataSource
import ui.mvi.BaseMviViewModel
import ui.mvi.Intent
import ui.mvi.SideEffect
import ui.mvi.UiState
import javax.inject.Inject

@HiltViewModel
class VolunteersViewModel @Inject constructor(
    private val jwtDataStoreDataSource: JwtDataStoreDataSource,
) : BaseMviViewModel<VolunteersState, VolunteersIntent, VolunteersSideEffect>(
    initialState = VolunteersState.initial(),
) {
    init {
        loadToken()
    }

    private fun loadToken() {
        reduce(
            newState = stateFlow.value.copy(
                accessToken = jwtDataStoreDataSource.loadTokens().accessToken,
                refreshToken = jwtDataStoreDataSource.loadTokens().refreshToken,
            ),
        )
    }
}

@Immutable
data class VolunteersState(
    val accessToken: AccessToken?,
    val refreshToken: RefreshToken?,
) : UiState() {
    companion object {
        fun initial() = VolunteersState(
            accessToken = null,
            refreshToken = null,
        )
    }
}

sealed class VolunteersIntent : Intent()

sealed class VolunteersSideEffect : SideEffect()
