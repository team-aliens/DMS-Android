package team.aliens.dms_android.feature.application

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms_android.feature._legacy.base.BaseMviViewModel
import team.aliens.dms_android.feature._legacy.base.MviIntent
import team.aliens.dms_android.feature._legacy.base.MviSideEffect
import team.aliens.dms_android.feature._legacy.base.MviState
import team.aliens.dms_android.domain.model.remains.CurrentAppliedRemainsOption
import team.aliens.dms_android.domain.model.studyroom.CurrentAppliedStudyRoom
import team.aliens.dms_android.domain.usecase.remain.FetchCurrentAppliedRemainsOptionUseCase
import team.aliens.dms_android.domain.usecase.studyroom.FetchCurrentAppliedStudyRoomUseCase
import javax.inject.Inject

@HiltViewModel
internal class ApplicationViewModel @Inject constructor(
    private val fetchCurrentAppliedStudyRoomUseCase: FetchCurrentAppliedStudyRoomUseCase,
    private val fetchCurrentAppliedRemainsOptionUseCase: FetchCurrentAppliedRemainsOptionUseCase,
) : BaseMviViewModel<ApplicationIntent, ApplicationState, ApplicationSideEffect>(
    initialState = ApplicationState.initial(),
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
                reduce(
                    newState = stateFlow.value.copy(
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
                reduce(
                    newState = stateFlow.value.copy(
                        currentAppliedRemainsOption = currentAppliedRemainsOption,
                    ),
                )
            }
        }
    }
}

internal sealed class ApplicationIntent : MviIntent

internal data class ApplicationState(
    val currentAppliedStudyRoom: CurrentAppliedStudyRoom?,
    val currentAppliedRemainsOption: CurrentAppliedRemainsOption?,
) : MviState {
    companion object {
        fun initial() = ApplicationState(
            currentAppliedStudyRoom = null,
            currentAppliedRemainsOption = null,
        )
    }
}

internal sealed class ApplicationSideEffect : MviSideEffect
