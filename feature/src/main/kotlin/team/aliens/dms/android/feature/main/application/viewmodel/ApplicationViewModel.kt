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
import team.aliens.dms.android.data.voting.model.AllVoteSearch
import team.aliens.dms.android.data.voting.repository.VotingRepository
import team.aliens.dms.android.network.latestudy.model.StudyApplicationStatusResponse
import java.time.LocalDate
import java.time.format.DateTimeParseException
import javax.inject.Inject

@HiltViewModel
internal class ApplicationViewModel @Inject constructor(
    private val votingRepository: VotingRepository,
    private val lateStudyRepository: LateStudyRepository,
    @ApplicationContext private val context: Context,
) : BaseStateViewModel<ApplicationState, Unit>(ApplicationState()) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("application_prefs", Context.MODE_PRIVATE)

    init {
        getAllVotes()
        loadAppliedRemainsTitle()
        loadAppliedLateStudy()
        fetchMyStudyApplicationStatus()
    }

    private fun getAllVotes() {
        viewModelScope.launch(Dispatchers.IO) {
            votingRepository.fetchAllVoteSearch()
                .onSuccess { votes ->
                    setState { it.copy(votes = votes.toPersistentList()) }
                }
                .onFailure {
                }
        }
    }

    private fun loadAppliedRemainsTitle() {
        val savedTitle = sharedPreferences.getString(KEY_APPLIED_REMAINS_TITLE, null)
        if (savedTitle != null) {
            setState { it.copy(remainApplicationTitle = savedTitle) }
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
                val title = studyApplicationStatus.toAppliedTitle()
                val statusUi = studyApplicationStatus.toUiStatus()

                setState { current ->
                    current.copy(
                        lateStudyApplicationTitle = title ?: current.lateStudyApplicationTitle,
                        lateStudyStatusUi = statusUi ?: current.lateStudyStatusUi,
                    )
                }
                if (title == null) {
                    sharedPreferences.edit().remove(KEY_APPLIED_LATE_STUDY_TITLE).apply()
                } else {
                    sharedPreferences.edit().putString(KEY_APPLIED_LATE_STUDY_TITLE, title).apply()
                }
            }
        }
    }

    internal fun setRemainApplication(title: String) {
        sharedPreferences.edit().putString(KEY_APPLIED_REMAINS_TITLE, title).apply()
        setState { it.copy(remainApplicationTitle = title) }
    }

    internal fun setLateStudyApplication(title: String) {
        sharedPreferences.edit().putString(KEY_APPLIED_LATE_STUDY_TITLE, title).apply()

        setState {
            it.copy(
                lateStudyApplicationTitle = title,
                lateStudyStatusUi = LateStudyStatusUi.PENDING,
            )
        }
    }

    companion object {
        private const val KEY_APPLIED_REMAINS_TITLE = "applied_remains_title"
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
    APPROVED,
    REJECTED,
    PENDING,
}

private fun StudyApplicationStatusResponse.toAppliedTitle(): String? {
    val today = LocalDate.now()
    val start = startDate.toLocalDateOrNull()
    val end = endDate.toLocalDateOrNull()

    return when (status) {
        "SECOND_APPROVED" -> approvedTitle(today, start, end)
        "PENDING" -> "신청 중"
        "REJECTED" -> rejectedTitle(today, start, end)
        else -> null
    }
}

private fun StudyApplicationStatusResponse.approvedTitle(
    today: LocalDate,
    start: LocalDate?,
    end: LocalDate?,
): String? {
    val actualEndDate = end ?: start
    if (actualEndDate == null || today.isAfter(actualEndDate)) return null
    return buildRangeText(start, end, "승인됨")
}

private fun StudyApplicationStatusResponse.rejectedTitle(
    today: LocalDate,
    start: LocalDate?,
    end: LocalDate?,
): String? {
    val rejectBaseDate = end ?: start
    if (rejectBaseDate == null || !rejectBaseDate.isEqual(today)) return null
    return buildRangeText(start, end, "거절됨") ?: "거절됨"
}

private fun StudyApplicationStatusResponse.buildRangeText(
    start: LocalDate?,
    end: LocalDate?,
    suffix: String,
): String? {
    return when {
        start != null && end != null -> {
            if (start == end) "$startDate $suffix"
            else "$startDate ~ $endDate $suffix"
        }
        start != null -> "$startDate $suffix"
        end != null -> "$endDate $suffix"
        else -> null
    }
}

private fun StudyApplicationStatusResponse.toUiStatus(): LateStudyStatusUi? {
    val today = LocalDate.now()
    val start = startDate.toLocalDateOrNull()
    val end = endDate.toLocalDateOrNull()

    return when (status) {
        "SECOND_APPROVED" -> {
            val actualEndDate = end ?: start
            if (actualEndDate != null && !today.isAfter(actualEndDate)) {
                LateStudyStatusUi.APPROVED
            } else {
                null
            }
        }
        "PENDING" -> LateStudyStatusUi.PENDING
        "REJECTED" -> {
            val rejectBaseDate = end ?: start
            if (rejectBaseDate != null && rejectBaseDate.isEqual(today)) {
                LateStudyStatusUi.REJECTED
            } else {
                null
            }
        }
        else -> null
    }
}

private fun String?.toLocalDateOrNull(): LocalDate? {
    if (this.isNullOrBlank()) return null
    return try {
        LocalDate.parse(this)
    } catch (_: DateTimeParseException) {
        null
    }
}
