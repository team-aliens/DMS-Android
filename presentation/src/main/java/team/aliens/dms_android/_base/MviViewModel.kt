package team.aliens.dms_android._base

import kotlinx.coroutines.flow.Flow

// TODO component's name must be changed to "BaseViewModel", removing old BaseViewModel(s)
internal abstract class MviViewModel<S : UiState, in E : UiEvent> {
    abstract val state: Flow<S>
}
