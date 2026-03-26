package team.aliens.dms.android.feature.vote.viewmodel

import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
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
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
internal class VoteViewModel @Inject constructor(
    private val voteRepository: VotingRepository,
    private val studentRepository: StudentRepository,
) : BaseStateViewModel<VoteState, VoteSideEffect>(VoteState()) {

    internal fun initState(vote: AllVoteSearch) {
        setState {
            it.copy(
                vote = vote
            )
        }
        fetchVotesByType()
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
                .onSuccess { student -> setState { it.copy(students = student.toPersistentList()) } }
                .onFailure {
                    sendEffect(VoteSideEffect.VoteLoadFail)
                }
        }
    }

    private fun getVoteItems() {
        viewModelScope.launch(Dispatchers.IO) {
            voteRepository.fetchCheckVotingItem(uiState.value.vote.id)
                .onSuccess { voteItems -> setState { it.copy(options = voteItems.toPersistentList()) } }
                .onFailure {
                    sendEffect(VoteSideEffect.VoteLoadFail)
                }
        }
    }

    private fun getCandidateModelStudents() {
        viewModelScope.launch(Dispatchers.IO) {
            voteRepository.fetchModelStudentCandidates(requestDate = today)
                .onSuccess { modelStudents -> setState { it.copy(modelStudent = modelStudents.toPersistentList()) } }
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
                    selectedId = uiState.value.selectId!!,
                ).onSuccess {
                    setState { uiState.value.copy(buttonEnabled = false, isLoading = false) }
                    sendEffect(VoteSideEffect.VoteSuccess)
                }.onFailure {
                    setState { uiState.value.copy(isLoading = false, buttonEnabled = true) }
                    Log.d("TEST", it.toString())
                    when (it.message?.drop(5)?.trim()) {
                        "409" -> sendEffect(VoteSideEffect.VoteConflict)
                        else -> sendEffect(VoteSideEffect.VoteFail)
                    }
                }
            }
        }
    }
}

@Immutable
internal data class VoteState(
    val vote: AllVoteSearch = AllVoteSearch(),
    val options: ImmutableList<VotingItem> = persistentListOf(),
    val students: ImmutableList<Student> = persistentListOf(),
    val modelStudent: ImmutableList<Student> = persistentListOf(),
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
