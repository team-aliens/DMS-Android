package team.aliens.dms_android.feature.application

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import team.aliens.dms_android._base.BaseViewModel
import team.aliens.dms_android._base.UiEvent
import team.aliens.dms_android._base.UiState
import team.aliens.domain.usecase.remain.FetchCurrentAppliedRemainsOptionUseCase
import team.aliens.domain.usecase.studyroom.FetchCurrentAppliedStudyRoomUseCase
import javax.inject.Inject

@HiltViewModel
class ApplicationViewModel @Inject constructor(
    private val fetchCurrentStudyRoomOptionUseCase: FetchCurrentAppliedStudyRoomUseCase,
    private val fetchCurrentRemainOptionUseCase: FetchCurrentAppliedRemainsOptionUseCase,
) : BaseViewModel<ApplicationState, UiEvent>() {

    override val _uiState: MutableStateFlow<ApplicationState> = MutableStateFlow(ApplicationState())

    internal fun fetchCurrentRemainOption() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                fetchCurrentRemainOptionUseCase()
            }.onSuccess {
                _uiState.emit(
                    _uiState.value.copy(
                        currentRemainOption = it.title,
                    ),
                )
            }.onFailure {
                when (it) {
                    is RuntimeException -> { // fixme 리팩토링 필요
                        _uiState.emit(
                            _uiState.value.copy(
                                currentRemainOption = ""
                            )
                        )
                    }
                }
            }
        }
    }

    internal fun fetchCurrentStudyRoomOption() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                fetchCurrentStudyRoomOptionUseCase()
            }.onSuccess {
                _uiState.emit(
                    _uiState.value.copy(
                        currentStudyRoomOption = it.floor floorAnd it.name
                    ),
                )
            }.onFailure {
                when (it) {
                    is RuntimeException -> { // fixme 리팩토링 필요
                        _uiState.emit(
                            _uiState.value.copy(
                                currentStudyRoomOption = ""
                            )
                        )
                    }
                }
            }
        }
    }

    override fun onEvent(uiEvent: UiEvent) {
        /* explicit blank */
    }
}

private infix fun Int.floorAnd(other: String): String {
    return "${this}층 $other"
}

data class ApplicationState(
    val currentStudyRoomOption: String = "",
    val currentRemainOption: String = "",
) : UiState
