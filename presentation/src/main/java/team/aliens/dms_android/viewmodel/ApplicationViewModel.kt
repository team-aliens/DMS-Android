package team.aliens.dms_android.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import team.aliens.dms_android._base.BaseEvent
import team.aliens.dms_android._base.BaseUiState
import team.aliens.dms_android._base.BaseViewModel
import team.aliens.domain.exception.NotFoundException
import team.aliens.domain.usecase.remain.RemoteFetchCurrentRemainOptionsUseCase
import team.aliens.domain.usecase.studyroom.RemoteFetchCurrentStudyRoomOptionUseCase

@HiltViewModel
class ApplicationViewModel @Inject constructor(
    private val fetchCurrentStudyRoomOptionUseCase: RemoteFetchCurrentStudyRoomOptionUseCase,
    private val fetchCurrentRemainOptionUseCase: RemoteFetchCurrentRemainOptionsUseCase,
) : BaseViewModel<ApplicationState, BaseEvent>() {

    override val _uiState: MutableStateFlow<ApplicationState> = MutableStateFlow(ApplicationState())

    internal fun fetchCurrentRemainOption() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                fetchCurrentRemainOptionUseCase.execute(Unit)
            }.onSuccess {
                _uiState.emit(
                    _uiState.value.copy(
                        currentRemainOption = it.title,
                    ),
                )
            }.onFailure {
                when(it){
                    is NotFoundException -> {
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
                fetchCurrentStudyRoomOptionUseCase.execute(Unit)
            }.onSuccess {
                _uiState.emit(
                    _uiState.value.copy(
                        currentStudyRoomOption = it.floor floorAnd it.name
                    ),
                )
            }.onFailure {
                when(it){
                    is NotFoundException -> {
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

    override fun onEvent(event: BaseEvent) {
        /* explicit blank */
    }
}

private infix fun Long.floorAnd(other: String): String {
    return "${this}층 $other"
}

data class ApplicationState(
    val currentStudyRoomOption: String = "",
    val currentRemainOption: String = "",
) : BaseUiState
