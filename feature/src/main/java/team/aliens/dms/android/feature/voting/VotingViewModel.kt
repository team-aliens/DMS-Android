package team.aliens.dms.android.feature.voting

import androidx.compose.runtime.mutableStateOf
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
import team.aliens.dms.android.data.voting.model.ModelStudentCandidates
import team.aliens.dms.android.data.voting.model.Vote
import team.aliens.dms.android.data.voting.model.VotingItem
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

    private val _buttonEnable = mutableStateOf(true)
    var buttonEnabled = _buttonEnable

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
            is VotingIntent.UpdateModelStudentStates -> this.updateModelStudentGradeInfo(
                grade = intent.grade,
            )
            is VotingIntent.UpdateStudentStates -> this.updateStudentGradeInfo(
                grade = intent.grade,
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
                        votingTopicCheckList = fetchCheckVotingItem,
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
    private fun updateModelStudentGradeInfo(grade: Int) {
        reduce(
            newState = stateFlow.value.copy(
                filteredModelStudentList = stateFlow.value.modelStudentCandidates.filter { it.studentGcn >= grade && it.studentGcn <= grade + 1000 }
            ),
        )
    }

    private fun updateStudentGradeInfo(grade: Int) {
        reduce(
            newState = stateFlow.value.copy(
                filteredStudentList = stateFlow.value.allStudentsList.filter { it.gradeClassNumber >= grade.toString() && it.gradeClassNumber <= (grade + 1000).toString() }
            ),
        )
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
    val votingTopicCheckList: List<VotingItem>,
    val filteredModelStudentList: List<ModelStudentCandidates>,
    val filteredStudentList: List<Student>,
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
            filteredStudentList = emptyList(),
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
    class UpdateModelStudentStates(val grade: Int) : VotingIntent()
    class UpdateStudentStates(val grade: Int) : VotingIntent()
}

sealed class VotingSideEffect : SideEffect()
