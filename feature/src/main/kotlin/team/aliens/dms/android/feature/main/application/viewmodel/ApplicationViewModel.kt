package team.aliens.dms.android.feature.main.application.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.ui.viewmodel.BaseStateViewModel
import team.aliens.dms.android.data.latestudy.repository.LateStudyRepository
import team.aliens.dms.android.data.remain.repository.RemainsRepository
import team.aliens.dms.android.data.voting.model.AllVoteSearch
import team.aliens.dms.android.data.voting.repository.VotingRepository
import team.aliens.dms.android.network.latestudy.model.StudyApplicationStatusResponse
import javax.inject.Inject

@HiltViewModel
internal class ApplicationViewModel @Inject constructor(
    private val votingRepository: VotingRepository,
    private val lateStudyRepository: LateStudyRepository,
    private val remainsRepository: RemainsRepository,
    @ApplicationContext private val context: Context,
) : BaseStateViewModel<ApplicationState, Unit>(ApplicationState()) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("application_prefs", Context.MODE_PRIVATE)
    private var remainApplicationTitleVersion = 0

    init {
        getAllVotes()
        fetchAppliedRemainsOption()
        loadAppliedLateStudy()
        fetchMyStudyApplicationStatus()
    }

    private fun getAllVotes() {
        viewModelScope.launch(Dispatchers.IO) {
            votingRepository.fetchAllVoteSearch()
                .onSuccess { votes ->
                    setState {
                        it.copy(votes = votes.toPersistentList())
                    }
                }
                .onFailure {
                }
        }
    }

    private fun fetchAppliedRemainsOption() {
        val titleVersion = remainApplicationTitleVersion

        viewModelScope.launch {
            remainsRepository.fetchAppliedRemainsOption()
                .onSuccess { appliedOption ->
                    if (titleVersion != remainApplicationTitleVersion) return@onSuccess

                    setState { it.copy(remainApplicationTitle = appliedOption?.title) }
                }
        }
    }

    private fun loadAppliedLateStudy() {
        val savedTitle = sharedPreferences.getString(KEY_APPLIED_LATE_STUDY_TITLE, null)

        if (savedTitle != null) {
            setState {
                it.copy(
                    lateStudyApplicationTitle = savedTitle,
                    lateStudyStatusUi = LateStudyStatusUi.PENDING,
                )
            }
        }
    }

    private fun fetchMyStudyApplicationStatus() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                lateStudyRepository.fetchMyStudyApplicationStatus()
            }.onSuccess { studyApplicationStatus ->
                if (studyApplicationStatus == null) {
                    clearLateStudyApplication()
                    return@onSuccess
                }

                val title = studyApplicationStatus.toAppliedTitle()
                val statusUi = studyApplicationStatus.toUiStatus()

                setState {
                    it.copy(
                        lateStudyApplicationTitle = title,
                        lateStudyStatusUi = statusUi,
                    )
                }

                if (title != null) {
                    sharedPreferences.edit()
                        .putString(KEY_APPLIED_LATE_STUDY_TITLE, title)
                        .apply()
                } else {
                    clearLateStudyApplication()
                }
            }.onFailure {
                // 서버 요청 실패 시에는 기존 SharedPreferences 값 유지
            }
        }
    }

    internal fun setRemainApplication(title: String) {
        remainApplicationTitleVersion += 1

        setState {
            it.copy(remainApplicationTitle = title)
        }
    }

    internal fun setLateStudyApplication(title: String) {
        sharedPreferences.edit()
            .putString(KEY_APPLIED_LATE_STUDY_TITLE, title)
            .apply()

        setState {
            it.copy(
                lateStudyApplicationTitle = title,
                lateStudyStatusUi = LateStudyStatusUi.PENDING,
            )
        }
    }

    private fun clearLateStudyApplication() {
        sharedPreferences.edit()
            .remove(KEY_APPLIED_LATE_STUDY_TITLE)
            .apply()

        setState {
            it.copy(
                lateStudyApplicationTitle = null,
                lateStudyStatusUi = null,
            )
        }
    }

    companion object {
        private const val KEY_APPLIED_LATE_STUDY_TITLE = "applied_late_study_title"
    }
}

@Immutable
data class ApplicationState(
    val votes: ImmutableList<AllVoteSearch> = persistentListOf(),
    val remainApplicationTitle: String? = null,
    val lateStudyApplicationTitle: String? = null,
    val lateStudyStatusUi: LateStudyStatusUi? = null,
)

enum class LateStudyStatusUi {
    FIRST_APPROVED,
    SECOND_APPROVED,
    REJECTED,
    PENDING,
}

private fun StudyApplicationStatusResponse.toAppliedTitle(): String? {
    return when (status) {
        "FIRST_APPROVED" -> "1차 승인"
        "SECOND_APPROVED" -> "최종 승인"
        "PENDING" -> "신청 중"
        "REJECTED" -> "거절됨"
        "EXPIRED" -> null
        else -> null
    }
}

private fun StudyApplicationStatusResponse.toUiStatus(): LateStudyStatusUi? {
    return when (status) {
        "FIRST_APPROVED" -> LateStudyStatusUi.FIRST_APPROVED
        "SECOND_APPROVED" -> LateStudyStatusUi.SECOND_APPROVED
        "PENDING" -> LateStudyStatusUi.PENDING
        "REJECTED" -> LateStudyStatusUi.REJECTED
        "EXPIRED" -> null
        else -> null
    }
}
