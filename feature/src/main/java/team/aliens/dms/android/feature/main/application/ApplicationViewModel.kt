package team.aliens.dms.android.feature.main.application

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.ui.mvi.BaseMviViewModel
import team.aliens.dms.android.core.ui.mvi.Intent
import team.aliens.dms.android.core.ui.mvi.SideEffect
import team.aliens.dms.android.core.ui.mvi.UiState
import team.aliens.dms.android.data.remains.model.AppliedRemainsOption
import team.aliens.dms.android.data.remains.repository.RemainsRepository
import team.aliens.dms.android.data.studyroom.model.AppliedStudyRoom
import team.aliens.dms.android.data.studyroom.repository.StudyRoomRepository
import javax.inject.Inject

@HiltViewModel
internal class ApplicationViewModel @Inject constructor(
    private val studyRoomRepository: StudyRoomRepository,
    private val remainsRepository: RemainsRepository,
) : BaseMviViewModel<ApplicationUiState, ApplicationIntent, ApplicationSideEffect>(
    initialState = ApplicationUiState.initial(),
),
    DefaultLifecycleObserver {

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        // TODO: onDispose를 이용하여 이벤트 넘기기
        viewModelScope.launch {
            fetchAppliedStudyRoom()
            fetchAppliedRemainsOption()
        }
    }

    private fun fetchAppliedStudyRoom() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                studyRoomRepository.fetchAppliedStudyRoom()
            }.onSuccess { appliedStudyRoom ->
                reduce(newState = stateFlow.value.copy(appliedStudyRoom = appliedStudyRoom))
            }
        }
    }

    private fun fetchAppliedRemainsOption() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                remainsRepository.fetchAppliedRemainsOption()
            }.onSuccess { appliedRemainsOption ->
                reduce(newState = stateFlow.value.copy(appliedRemainsOption = appliedRemainsOption))
            }
        }
    }
}

internal data class ApplicationUiState(
    val appliedStudyRoom: AppliedStudyRoom?,
    val appliedRemainsOption: AppliedRemainsOption?,
) : UiState() {
    companion object {
        fun initial() = ApplicationUiState(
            appliedStudyRoom = null,
            appliedRemainsOption = null,
        )
    }
}

internal sealed class ApplicationIntent : Intent()

internal sealed class ApplicationSideEffect : SideEffect()
