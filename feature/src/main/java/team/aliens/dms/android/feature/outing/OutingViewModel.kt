package team.aliens.dms.android.feature.outing

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import team.aliens.dms.android.core.ui.mvi.BaseMviViewModel
import team.aliens.dms.android.core.ui.mvi.Intent
import team.aliens.dms.android.core.ui.mvi.SideEffect
import team.aliens.dms.android.core.ui.mvi.UiState
import team.aliens.dms.android.data.outing.model.CurrentAppliedOutingApplication
import team.aliens.dms.android.data.outing.model.OutingApplicationTime
import team.aliens.dms.android.data.outing.repository.OutingRepository
import team.aliens.dms.android.data.student.model.Student
import team.aliens.dms.android.data.student.repository.StudentRepository
import team.aliens.dms.android.shared.date.util.now
import team.aliens.dms.android.shared.date.util.timeNow
import team.aliens.dms.android.shared.date.util.today
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class OutingViewModel @Inject constructor(
    private val outingRepository: OutingRepository,
    private val studentRepository: StudentRepository,
) : BaseMviViewModel<OutingUiState, OutingIntent, OutingSideEffect>(
    initialState = OutingUiState.initial(),
) {
    init {
        fetchOutingApplicationTime()
        fetchCurrentAppliedOutingApplication()
        fetchOutingTypes()
        fetchStudents()
        fetchOutingDate()
        fetchApplicationState()
    }

    override fun processIntent(intent: OutingIntent) {
        when (intent) {
            is OutingIntent.CancelCurrentApplication -> cancelApplication()
            is OutingIntent.UpdateSelectedOutingType -> updateSelectedOutingType(value = intent.value)
            is OutingIntent.UpdateReason -> updateReason(value = intent.value)
            is OutingIntent.UpdateOutingStartTime -> updateOutingStartTime(value = intent.value)
            is OutingIntent.UpdateOutingEndTime -> updateOutingEndTime(value = intent.value)
            OutingIntent.ApplyOuting -> applyOuting()
            is OutingIntent.SelectStudent -> selectStudent(
                student = intent.student,
                select = intent.select,
            )

            OutingIntent.FetchCurrentAppliedOutingApplication -> fetchCurrentAppliedOutingApplication()
        }
    }

    private fun fetchOutingApplicationTime() = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            outingRepository.fetchOutingApplicationTimes(dayOfWeek = today.dayOfWeek)
        }.onSuccess { fetchedApplicationTime ->
            if (fetchedApplicationTime.isNotEmpty()) {
                reduce(
                    newState = stateFlow.value.copy(
                        outingApplicationTime = fetchedApplicationTime.first(),
                    ),
                )
            }
        }.onFailure {
            it.printStackTrace()
        }
    }

    private fun fetchCurrentAppliedOutingApplication() = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            outingRepository.fetchCurrentAppliedOutingApplication()
        }.onSuccess { fetchedOutingApplication ->
            reduce(
                newState = stateFlow.value.copy(
                    currentAppliedOutingApplication = fetchedOutingApplication,
                ),
            )
        }.onFailure {
            postSideEffect(OutingSideEffect.CurrentAppliedOutingApplicationNotFound)
            it.printStackTrace()
        }
    }

    private fun cancelApplication() = viewModelScope.launch(Dispatchers.IO) {
        val capturedState = stateFlow.value
        runCatching {
            outingRepository.cancelOuting(
                applicationId = capturedState.currentAppliedOutingApplication!!.id,
            )
        }.onSuccess {
            reduce(
                newState = capturedState.copy(
                    currentAppliedOutingApplication = null,
                ),
            )
        }
    }

    private fun fetchOutingDate() {
        val captureOutingDate: LocalDate = stateFlow.value.outingDate
        if (now.hour >= 20) {
            reduce(
                newState = stateFlow.value.copy(outingDate = captureOutingDate.plusDays(1)),
            )
        }
    }

    private fun fetchApplicationState() {
        val isApplicationState = now.hour >= 20 || now.hour <= 11
        reduce(newState = stateFlow.value.copy(isApplicationState = isApplicationState))
    }

    private fun fetchOutingTypes() = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            outingRepository.fetchOutingTypes(null)
        }.onSuccess { fetchedOutingTypes ->
            reduce(
                newState = stateFlow.value.copy(
                    outingTypes = fetchedOutingTypes,
                ),
            )
        }
    }

    private fun updateSelectedOutingType(value: String) = reduce(
        newState = stateFlow.value.copy(
            selectedOutingType = value,
        ),
    )

    private fun updateReason(value: String) = reduce(
        newState = stateFlow.value.copy(
            reason = value,
        ),
    )

    private fun updateOutingStartTime(value: LocalTime) = reduce(
        newState = stateFlow.value.copy(
            selectedOutingStartTime = value,
        ),
    )

    private fun updateOutingEndTime(value: LocalTime) = reduce(
        newState = stateFlow.value.copy(
            selectedOutingEndTime = value,
        ),
    )

    private fun applyOuting() = run {
        val capturedState = stateFlow.value
        if (capturedState.selectedOutingType == null) {
            postSideEffect(OutingSideEffect.OutingTypeNotSelected)
            return@run
        }

        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                outingRepository.applyOuting(
                    date = capturedState.outingDate,
                    startTime = capturedState.selectedOutingStartTime,
                    endTime = capturedState.selectedOutingEndTime,
                    type = capturedState.selectedOutingType,
                    reason = capturedState.reason.ifBlank { null },
                    companionIds = capturedState.selectedStudents.map { it.id },
                )
            }.onSuccess { applicationId ->
                reduce(
                    newState = capturedState.copy(
                        applicationId = applicationId,
                    ),
                )
                postSideEffect(OutingSideEffect.OutingApplicationSuccess(applicationId))
            }.onFailure {
                postSideEffect(OutingSideEffect.OutingApplicationTimeError)
            }
        }
    }

    private fun fetchStudents() = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            studentRepository.fetchStudents()
        }.onSuccess { fetchedStudents ->
            reduce(
                newState = stateFlow.value.copy(
                    students = fetchedStudents,
                ),
            )
        }
    }

    private fun selectStudent(
        student: Student,
        select: Boolean,
    ) = run {
        val capturedState = stateFlow.value
        reduce(
            newState = capturedState.copy(
                selectedStudents = if (select) {
                    capturedState.selectedStudents.toMutableList().apply {
                        add(student)
                    }
                } else {
                    capturedState.selectedStudents.toMutableList().apply {
                        remove(student)
                    }
                },
            ),
        )
    }
}

data class OutingUiState(
    val outingApplicationTime: OutingApplicationTime?,
    val currentAppliedOutingApplication: CurrentAppliedOutingApplication?,
    val outingTypes: List<String>?,
    val selectedOutingType: String?,
    val reason: String,
    val outingDate: LocalDate,
    val selectedOutingStartTime: LocalTime,
    val selectedOutingEndTime: LocalTime,
    val companionIds: List<UUID>,
    val students: List<Student>?,
    val selectedStudents: List<Student>,
    val applicationId: UUID?,
    val isApplicationState: Boolean,
) : UiState() {
    companion object {
        fun initial(): OutingUiState {
            return OutingUiState(
                outingApplicationTime = null,
                currentAppliedOutingApplication = null,
                outingTypes = null,
                selectedOutingType = null,
                reason = "",
                outingDate = today,
                // TODO: remove hard-coded string resources from viewmodel
                selectedOutingEndTime = timeNow,
                selectedOutingStartTime = timeNow,
                companionIds = emptyList(),
                students = null,
                selectedStudents = emptyList(),
                applicationId = null,
                isApplicationState = false,
            )
        }
    }
}

sealed class OutingIntent : Intent() {
    data object CancelCurrentApplication : OutingIntent()
    class UpdateSelectedOutingType(val value: String) : OutingIntent()
    class UpdateReason(val value: String) : OutingIntent()
    class UpdateOutingStartTime(val value: LocalTime) : OutingIntent()
    class UpdateOutingEndTime(val value: LocalTime) : OutingIntent()
    data object ApplyOuting : OutingIntent()
    class SelectStudent(
        val student: Student,
        val select: Boolean,
    ) : OutingIntent()

    data object FetchCurrentAppliedOutingApplication : OutingIntent()
}

sealed class OutingSideEffect : SideEffect() {
    data object CurrentAppliedOutingApplicationNotFound : OutingSideEffect()
    data object OutingTypeNotSelected : OutingSideEffect()
    class OutingApplicationSuccess(val applicationId: UUID) : OutingSideEffect()
    data object OutingApplicationTimeError : OutingSideEffect()
}
