package team.aliens.dms.android.feature.voting

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDateTime
import team.aliens.dms.android.core.ui.mvi.BaseMviViewModel
import team.aliens.dms.android.core.ui.mvi.Intent
import team.aliens.dms.android.core.ui.mvi.SideEffect
import team.aliens.dms.android.core.ui.mvi.UiState
import team.aliens.dms.android.data.remains.model.AppliedRemainsOption
import team.aliens.dms.android.data.remains.repository.RemainsRepository
import team.aliens.dms.android.data.studyroom.model.AppliedStudyRoom
import team.aliens.dms.android.data.studyroom.repository.StudyRoomRepository
import team.aliens.dms.android.data.voting.model.AllVoteSearch
import team.aliens.dms.android.data.voting.model.CheckVotingItem
import team.aliens.dms.android.data.voting.model.ModelStudentCandidates
import team.aliens.dms.android.data.voting.model.Vote
import team.aliens.dms.android.data.voting.repository.VotingRepository
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class VotingViewModel @Inject constructor(
    private val remainsRepository: RemainsRepository,
    private val votingRepository: VotingRepository,
    private val studyRoomRepository: StudyRoomRepository,
) : BaseMviViewModel<VotingUiState, VotingIntent, VotingSideEffect>(
    initialState = VotingUiState.initial(),
) {
    init {
        fetchAllVoteSearch()
        fetchAppliedRemainsOption()
    }

    override fun processIntent(intent: VotingIntent) {
        when (intent) {
            is VotingIntent.UpdateVotingItem -> updateCheckVotingItem(
                intent.voteOption,
            )
            is VotingIntent.UpdateModelStudent -> updateModelStudentList(
                intent.requestDate
            )
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
            }.onFailure {
                it.message
            }
        }
    }

    private fun updateCheckVotingItem(voteOption: AllVoteSearch) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                votingRepository.fetchCheckVotingItem(
                    votingTopicId = voteOption.id,
                )
            }.onSuccess { fetchCheckVotingItem ->
                reduce(
                    newState = stateFlow.value.copy(
                        votingTopicCheckList = listOf(fetchCheckVotingItem),
                        uiStateTitle = voteOption.topicName,
                    ),
                )
            }.onFailure {
                Log.d("TEST", it.message.toString())
            }
        }
    }
    // 뷰모델 확인
    private fun updateModelStudentList(requestDate: LocalDateTime) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                votingRepository.fetchModelStudentCandidates(
                    requestDate = requestDate,
                )
            }.onSuccess { fetchModelList ->
                reduce(
                    newState = stateFlow.value.copy(
                        modelStudentCandidates = listOf(fetchModelList),
                    ),
                )
            }.onFailure {
                it.printStackTrace()
            }
        }
    }

    private fun updateGradeInfo(id: UUID): Boolean =
        reduce(
            newState = stateFlow.value.copy(
                votingButtonEnabled = stateFlow.value.modelStudentCandidates.find { it.id == id }?.studentGcn!! >= 2000,
            ),
        )

    private fun fetchAppliedRemainsOption() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                remainsRepository.fetchAppliedRemainsOption()
            }.onSuccess { appliedRemainsOption ->
                reduce(newState = stateFlow.value.copy(appliedRemainsOption = appliedRemainsOption))
            }
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
}

data class VotingUiState(
    val selectedVotingOptionId: UUID?,
    val pageCountState: Int,
    val uiStateTitle: String,
    val modelStudentCandidates: List<ModelStudentCandidates>,
    val modelStudentVoteList: List<AllVoteSearch>,
    val selectedVoteList: List<AllVoteSearch>,
    val studentVoteList: List<AllVoteSearch>,
    val approvalVoteList: List<AllVoteSearch>,
    val votingTopicCheckList: List<CheckVotingItem>,
    val votingButtonEnabled: Boolean,
    val appliedStudyRoom: AppliedStudyRoom?,
    val appliedRemainsOption: AppliedRemainsOption?,
) : UiState() {
    companion object {
        fun initial() = VotingUiState(
            selectedVotingOptionId = null,
            pageCountState = 0,
            uiStateTitle = "",
            modelStudentCandidates = emptyList(),
            modelStudentVoteList = emptyList(),
            selectedVoteList = emptyList(),
            studentVoteList = emptyList(),
            approvalVoteList = emptyList(),
            votingTopicCheckList = emptyList(),
            votingButtonEnabled = false,
            appliedStudyRoom = null,
            appliedRemainsOption = null,
        )
    }
}

sealed class VotingIntent : Intent() {
    class UpdateVotingItem(val voteOption: AllVoteSearch) : VotingIntent()
    class UpdateModelStudent(val requestDate: LocalDateTime) : VotingIntent()
}

sealed class VotingSideEffect : SideEffect() {
    data class MoveToVoteDetail(val voteOption: AllVoteSearch) : VotingSideEffect()
}
