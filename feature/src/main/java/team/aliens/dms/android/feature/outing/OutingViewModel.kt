package team.aliens.dms.android.feature.outing

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDateTime
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
        }
    }

    private fun fetchOutingApplicationTime() = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            outingRepository.fetchOutingApplicationTimes(dayOfWeek = today.dayOfWeek)
        }.onSuccess { fetchedApplicationTime ->
            reduce(
                newState = stateFlow.value.copy(
                    outingApplicationTime = fetchedApplicationTime.first(),
                ),
            )
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
        }
    }

    private fun cancelApplication() = viewModelScope.launch(Dispatchers.IO) {

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

    private fun updateOutingStartTime(value: String) = reduce(
        newState = stateFlow.value.copy(
            selectedOutingStartTime = value,
        ),
    )


    private fun updateOutingEndTime(value: String) = reduce(
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
                /*outingRepository.applyOuting(
                    date = capturedState.capturedNow.toLocalDate(),
                    startTime = capturedState.selectedOutingStartTime + ":00",
                    endTime = capturedState.selectedOutingEndTime + ":00",
                    type = capturedState.selectedOutingType,
                    reason = if (capturedState.reason.isBlank()) {
                        null
                    } else {
                        capturedState.reason
                    },
                    companionIds =
                )*/
            }
        }
    }

    private fun fetchStudents() = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            // FIXME
            // studentRepository.fetchStudents()
            listOf(
                Student(
                    id = UUID.randomUUID(),
                    name = "박준수",
                    gradeClassNumber = "3211",
                    profileImageUrl = "www.naver.com",
                ),
                Student(
                    id = UUID.randomUUID(),
                    name = "준박수",
                    gradeClassNumber = "1234",
                    profileImageUrl = "www.naver.com",
                ),
                Student(
                    id = UUID.randomUUID(),
                    name = "박준혁",
                    gradeClassNumber = "2211",
                    profileImageUrl = "www.naver.com",
                ),
                Student(
                    id = UUID.randomUUID(),
                    name = "수준봐",
                    gradeClassNumber = "1111",
                    profileImageUrl = "www.naver.com",
                ),
                Student(
                    id = UUID.randomUUID(),
                    name = "코딱지",
                    gradeClassNumber = "4411",
                    profileImageUrl = "www.naver.com",
                ),
                Student(
                    id = UUID.randomUUID(),
                    name = "빅준자",
                    gradeClassNumber = "9213",
                    profileImageUrl = "www.naver.com",
                ),
            )
        }.onSuccess { fetchedStudents ->
            println(fetchedStudents)
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
            )
        )
    }
}

data class OutingUiState(
    val outingApplicationTime: OutingApplicationTime?,
    val currentAppliedOutingApplication: CurrentAppliedOutingApplication?,
    val outingTypes: List<String>?,
    val selectedOutingType: String?,
    val reason: String,
    val capturedNow: LocalDateTime,
    val selectedOutingStartTime: String,
    val selectedOutingEndTime: String,
    val companionIds: List<UUID>,
    val students: List<Student>?,
    val selectedStudents: List<Student>,
) : UiState() {
    companion object {
        fun initial(): OutingUiState {
            val capturedNow = now
            return OutingUiState(
                outingApplicationTime = null,
                currentAppliedOutingApplication = null,
                outingTypes = null,
                selectedOutingType = null,
                reason = "",
                capturedNow = capturedNow,
                // TODO: remove hard-coded string resources from viewmodel
                selectedOutingEndTime = "${capturedNow.hour}:${capturedNow.minute}",
                selectedOutingStartTime = "${capturedNow.hour}:${capturedNow.minute}",
                companionIds = emptyList(),
                students = null,
                selectedStudents = emptyList(),
            )
        }
    }
}

sealed class OutingIntent : Intent() {
    data object CancelCurrentApplication : OutingIntent()
    class UpdateSelectedOutingType(val value: String) : OutingIntent()
    class UpdateReason(val value: String) : OutingIntent()
    class UpdateOutingStartTime(val value: String) : OutingIntent()
    class UpdateOutingEndTime(val value: String) : OutingIntent()
    data object ApplyOuting : OutingIntent()
    class SelectStudent(
        val student: Student,
        val select: Boolean,
    ) : OutingIntent()
}

sealed class OutingSideEffect : SideEffect() {
    data object CurrentAppliedOutingApplicationNotFound : OutingSideEffect()
    data object OutingTypeNotSelected : OutingSideEffect()
}
