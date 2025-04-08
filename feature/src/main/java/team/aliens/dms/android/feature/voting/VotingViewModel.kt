package team.aliens.dms.android.feature.voting

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import team.aliens.dms.android.core.ui.mvi.BaseMviViewModel
import team.aliens.dms.android.core.ui.mvi.Intent
import team.aliens.dms.android.core.ui.mvi.SideEffect
import team.aliens.dms.android.core.ui.mvi.UiState
import team.aliens.dms.android.data.student.model.Student
import team.aliens.dms.android.data.student.repository.StudentRepository
import team.aliens.dms.android.data.voting.model.AllVoteSearch
import team.aliens.dms.android.data.voting.model.CheckVotingItem
import team.aliens.dms.android.data.voting.model.ModelStudentCandidates
import team.aliens.dms.android.data.voting.model.Vote
import team.aliens.dms.android.data.voting.repository.VotingRepository
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class VotingViewModel @Inject constructor(
    private val votingRepository: VotingRepository,
    private val studentRepository: StudentRepository
) : BaseMviViewModel<VotingUiState, VotingIntent, VotingSideEffect>(
    initialState = VotingUiState.initial(),
) {
    init {
        fetchAllVoteSearch()
        fetchStudents()
    }

    override fun processIntent(intent: VotingIntent) {
        when (intent) {
            is VotingIntent.UpdateVotingItem -> this.updateCheckVotingItem(
                intent.voteOptionId,
            )
            is VotingIntent.UpdateModelStudent -> this.updateModelStudentList(
                intent.requestDate,
            )
            is VotingIntent.CreateVoteTable -> this.fetchCreateVoteTable(
                votingTopicId = intent.votingTopicId,
                selectedId = intent.selectedId,
            )
            is VotingIntent.SetVoteTopicId -> this.setVoteTopicId(
                voteTopicId = intent.voteTopicId,
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

    internal fun updateCheckVotingItem(voteOptionId: UUID) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                votingRepository.fetchCheckVotingItem(
                    votingTopicId = voteOptionId,
                )
            }.onSuccess { fetchCheckVotingItem ->
                reduce(
                    newState = stateFlow.value.copy(
                        votingTopicCheckList = listOf(fetchCheckVotingItem),
                    ),
                )
            }
        }
    }

    internal fun updateModelStudentList(requestDate: LocalDate) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                votingRepository.fetchModelStudentCandidates(
                    requestDate = requestDate,
                )
            }.onSuccess { fetchModelList ->
                reduce(
                    newState = stateFlow.value.copy(
                        modelStudentCandidates = fetchModelList,
                    ),
                )
            }.onFailure {
                it.printStackTrace()
            }
        }
    }
    // 학년 필터링
    private fun updateGradeInfo(grade: Long): Boolean =
        reduce(
            newState = stateFlow.value.copy(
                filteredModelStudentList = stateFlow.value.modelStudentCandidates.filter { it.studentGcn >= grade }
            ),
        )
    // 버튼 enable 상태 변경
    private fun updateButtonEnableTime() {

    }

    private fun fetchCreateVoteTable(votingTopicId: UUID, selectedId: UUID) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                votingRepository.fetchCreateVotingItem(
                    votingTopicId = votingTopicId,
                    selectedId = selectedId,
                )
            }.onSuccess {

            }
        }
    }

    private fun fetchStudents() = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            studentRepository.fetchStudents()
        }.onSuccess { fetchedStudents ->
            reduce(
                newState = stateFlow.value.copy(
                    allStudentsList = fetchedStudents,
                ),
            )
        }
    }

    private fun setVoteTopicId(voteTopicId: UUID) =
        reduce(
            newState = stateFlow.value.copy(
                voteTopicId = voteTopicId,
            ),
        )


}

data class VotingUiState(
    val selectedVotingOptionId: UUID?,
    val pageCountState: Int,
    val voteTopicId: UUID?,
    val modelStudentCandidates: List<ModelStudentCandidates>,
    val modelStudentVoteList: List<AllVoteSearch>,
    val selectedVoteList: List<AllVoteSearch>,
    val studentVoteList: List<AllVoteSearch>,
    val approvalVoteList: List<AllVoteSearch>,
    val votingTopicCheckList: List<CheckVotingItem>,
    val filteredModelStudentList: List<ModelStudentCandidates>,
    val votingButtonEnabled: Boolean,
    val allStudentsList: List<Student>,
) : UiState() {
    companion object {
        fun initial() = VotingUiState(
            selectedVotingOptionId = null,
            pageCountState = 0,
            voteTopicId = null,
            modelStudentCandidates = emptyList(),
            modelStudentVoteList = emptyList(),
            selectedVoteList = emptyList(),
            studentVoteList = emptyList(),
            approvalVoteList = emptyList(),
            votingTopicCheckList = emptyList(),
            filteredModelStudentList = emptyList(),
            votingButtonEnabled = false,
            allStudentsList = emptyList(),
        )
    }
}

sealed class VotingIntent : Intent() {
    class UpdateVotingItem(val voteOptionId: UUID) : VotingIntent()
    class UpdateModelStudent(val requestDate: LocalDate) : VotingIntent()
    class CreateVoteTable(val votingTopicId: UUID, val selectedId: UUID) : VotingIntent()
    class SetVoteTopicId(val voteTopicId: UUID) : VotingIntent()
}

sealed class VotingSideEffect : SideEffect() {
    data class MoveToVoteDetail(val voteOption: AllVoteSearch) : VotingSideEffect()
}
