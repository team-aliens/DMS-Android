package team.aliens.dms.android.feature.main.application

import android.util.Log
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
import team.aliens.dms.android.data.voting.model.AllVoteSearch
import team.aliens.dms.android.data.voting.model.Vote
import team.aliens.dms.android.data.voting.repository.VotingRepository
import javax.inject.Inject

@HiltViewModel
internal class ApplicationViewModel @Inject constructor(
    private val studyRoomRepository: StudyRoomRepository,
    private val remainsRepository: RemainsRepository,
    private val votingRepository: VotingRepository,
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
            fetchAllVoteSearch()
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

    private fun fetchAllVoteSearch() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                votingRepository.fetchAllVoteSearch()
            }.onSuccess { fetchedVoteSearch ->
                reduce(
                    newState = stateFlow.value.copy(
                        modelStudentVoteList = fetchedVoteSearch.filter { it.voteType == Vote.MODEL_STUDENT_VOTE },
                        selectedVoteList = fetchedVoteSearch.filter { it.voteType == Vote.OPTION_VOTE },
                        studentVoteList = fetchedVoteSearch.filter { it.voteType == Vote.STUDENT_VOTE },
                        approvalVoteList = fetchedVoteSearch.filter { it.voteType == Vote.APPROVAL_VOTE },
                    ),
                )
            }
        }
    }
}

internal data class ApplicationUiState(
    val appliedStudyRoom: AppliedStudyRoom?,
    val appliedRemainsOption: AppliedRemainsOption?,
    val modelStudentVoteList: List<AllVoteSearch>,
    val selectedVoteList: List<AllVoteSearch>,
    val studentVoteList: List<AllVoteSearch>,
    val approvalVoteList: List<AllVoteSearch>,
) : UiState() {
    companion object {
        fun initial() = ApplicationUiState(
            appliedStudyRoom = null,
            appliedRemainsOption = null,
            modelStudentVoteList = emptyList(),
            selectedVoteList = emptyList(),
            studentVoteList = emptyList(),
            approvalVoteList = emptyList(),
        )
    }
}

internal sealed class ApplicationIntent : Intent()

internal sealed class ApplicationSideEffect : SideEffect()
