package team.aliens.dms.android.feature.vote.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.network.exception.ConflictException
import team.aliens.dms.android.core.ui.viewmodel.BaseStateViewModel
import team.aliens.dms.android.data.student.model.Student
import team.aliens.dms.android.data.student.repository.StudentRepository
import team.aliens.dms.android.data.voting.model.AllVoteSearch
import team.aliens.dms.android.data.voting.model.Vote
import team.aliens.dms.android.data.voting.model.VotingItem
import team.aliens.dms.android.data.voting.repository.VotingRepository
import team.aliens.dms.android.shared.date.toLocalDateTime
import team.aliens.dms.android.shared.date.util.today
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
internal class VoteViewModel @Inject constructor(
    private val voteRepository: VotingRepository,
    private val studentRepository: StudentRepository,
) : BaseStateViewModel<VoteState, VoteSideEffect>(VoteState()) {
     
    init {
        fetchVotesByType()
    }

    internal fun initState(title: String, startTime: String, endTime: String) {
        setState {
            it.copy(
                vote = it.vote.copy(topicName = title, startTime = startTime.toLocalDateTime(), endTime = endTime.toLocalDateTime()),
            )
        }
    }

    private fun fetchVotesByType() {
        when (uiState.value.vote.voteType) {
            Vote.OPTION_VOTE -> getVoteItems()
            Vote.STUDENT_VOTE -> getStudents()
            Vote.APPROVAL_VOTE -> getVoteItems()
            Vote.MODEL_STUDENT_VOTE -> getCandidateModelStudents()
        }
    }

    private fun getStudents() {
        viewModelScope.launch(Dispatchers.IO) {
            studentRepository.fetchStudents()
                .onSuccess { student -> setState { it.copy(students = student) } }
                .onFailure {
                    sendEffect(VoteSideEffect.VoteLoadFail)
                }
        }
    }

    private fun getVoteItems() {
        viewModelScope.launch(Dispatchers.IO) {
            voteRepository.fetchCheckVotingItem(uiState.value.vote.id)
                .onSuccess { voteItems -> setState { it.copy(options = voteItems) } }
                .onFailure {
                    sendEffect(VoteSideEffect.VoteLoadFail)
                }
        }
    }

    private fun getCandidateModelStudents() {
        viewModelScope.launch(Dispatchers.IO) {
            voteRepository.fetchModelStudentCandidates(requestDate = today)
                .onSuccess { modelStudents -> setState { it.copy(modelStudent = modelStudents) } }
                .onFailure {
                    sendEffect(VoteSideEffect.VoteLoadFail)
                }
        }
    }

    internal fun setSelectId(selectId: UUID) {
        setState { it.copy(selectId = selectId) }
        setButtonEnabled()
    }

    private fun setButtonEnabled() {
        val isSelectIdNotNull = uiState.value.selectId != null
        setState { uiState.value.copy(buttonEnabled = isSelectIdNotNull) }
    }

    internal fun postVote() {
        with(uiState.value) {
            viewModelScope.launch(Dispatchers.IO) {
                setState { uiState.value.copy(isLoading = true, buttonEnabled = false) }
                voteRepository.fetchCreateVotingItem(
                    votingTopicId = uiState.value.vote.id,
                    selectedId = uiState.value.selectId ?: UUID.randomUUID(),
                ).onSuccess {
                    setState { uiState.value.copy(buttonEnabled = false, isLoading = false) }
                    sendEffect(VoteSideEffect.VoteSuccess)
                }.onFailure {
                    setState { uiState.value.copy(isLoading = false, buttonEnabled = true) }
                    when (it) {
                        is ConflictException -> sendEffect(VoteSideEffect.VoteConflict)
                        else -> sendEffect(VoteSideEffect.VoteFail)
                    }
                }
            }
        }
    }
}

internal data class VoteState(
    val vote: AllVoteSearch = AllVoteSearch(),
    val options: List<VotingItem> = emptyList(),
    val students: List<Student> = emptyList(),
    val modelStudent: List<Student> = emptyList(),
    val selectId: UUID? = null,
    val buttonEnabled: Boolean = false,
    val isLoading: Boolean = false,
)

internal sealed interface VoteSideEffect {
    data object VoteSuccess : VoteSideEffect
    data object VoteConflict : VoteSideEffect
    data object VoteFail : VoteSideEffect
    data object VoteLoadFail : VoteSideEffect
}
