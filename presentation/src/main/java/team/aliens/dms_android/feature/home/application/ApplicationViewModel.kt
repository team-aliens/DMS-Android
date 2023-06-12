package team.aliens.dms_android.feature.home.application

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms_android.base.MviViewModel
import team.aliens.dms_android.base.UiEvent
import team.aliens.dms_android.base.UiState
import team.aliens.domain.model.remains.CurrentAppliedRemainsOption
import team.aliens.domain.model.studyroom.CurrentAppliedStudyRoom
import team.aliens.domain.usecase.remain.FetchCurrentAppliedRemainsOptionUseCase
import team.aliens.domain.usecase.studyroom.FetchCurrentAppliedStudyRoomUseCase
import javax.inject.Inject

@HiltViewModel
internal class ApplicationViewModel @Inject constructor(
    private val fetchCurrentAppliedStudyRoomUseCase: FetchCurrentAppliedStudyRoomUseCase,
    private val fetchCurrentAppliedRemainsOptionUseCase: FetchCurrentAppliedRemainsOptionUseCase,
) : MviViewModel<ApplicationUiState, ApplicationUiEvent>(
    initialState = ApplicationUiState.initial(),
) {
    init {
        fetchCurrentAppliedStudyRoom()
        fetchCurrentAppliedRemainsOption()
    }

    private fun fetchCurrentAppliedStudyRoom() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                fetchCurrentAppliedStudyRoomUseCase()
            }.onSuccess { fetchedCurrentAppliedStudyRoom ->
                setState(
                    newState = uiState.value.copy(
                        currentAppliedStudyRoom = fetchedCurrentAppliedStudyRoom,
                    ),
                )
            }
        }
    }

    private fun fetchCurrentAppliedRemainsOption() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                fetchCurrentAppliedRemainsOptionUseCase()
            }.onSuccess { currentAppliedRemainsOption ->
                setState(
                    newState = uiState.value.copy(
                        currentAppliedRemainsOption = currentAppliedRemainsOption,
                    ),
                )
            }
        }
    }
}

internal data class ApplicationUiState(
    val currentAppliedStudyRoom: CurrentAppliedStudyRoom?,
    val currentAppliedRemainsOption: CurrentAppliedRemainsOption?,
) : UiState {
    companion object {
        fun initial() = ApplicationUiState(
            currentAppliedStudyRoom = null,
            currentAppliedRemainsOption = null,
        )
    }
}

internal sealed class ApplicationUiEvent : UiEvent
