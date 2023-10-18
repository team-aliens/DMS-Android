package team.aliens.dms.android.feature.studyroom.list

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import team.aliens.dms.android.feature._legacy.base.BaseViewModel2
import team.aliens.dms.android.feature._legacy.util.extractHourFromDate
import team.aliens.dms.android.domain._legacy.exception.RemoteException
import team.aliens.dms.android.domain.model.studyroom.FetchAvailableStudyRoomTimesOutput
import team.aliens.dms.android.domain.model.studyroom.FetchStudyRoomsInput
import team.aliens.dms.android.domain.model.studyroom.FetchStudyRoomsOutput
import team.aliens.dms.android.domain.usecase.studyroom.FetchAvailableStudyRoomTimesUseCase
import team.aliens.dms.android.domain.usecase.studyroom.FetchStudyRoomApplicationTimeUseCase
import team.aliens.dms.android.domain.usecase.studyroom.FetchStudyRoomsUseCase
import team.aliens.dms.android.feature._legacy.base.UiState
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class StudyRoomListViewModel @Inject constructor(
    private val fetchStudyRoomsUseCase: FetchStudyRoomsUseCase,
    private val studyRoomApplyTimeUseCase: FetchStudyRoomApplicationTimeUseCase,
    private val studyRoomAvailableTimeListUseCase: FetchAvailableStudyRoomTimesUseCase,
) : BaseViewModel2<StudyRoomListUiState, StudyRoomListViewModel.UiEvent>() {

    init {
        fetchApplyTime()
    }

    sealed class UiEvent : team.aliens.dms.android.feature._legacy.base.UiEvent {

        data class FetchStudyRooms(
            val timeSlot: UUID,
        ) : UiEvent()

        internal object FetchStudyRoomAvailableTimes : UiEvent()

        internal class FilterStudyRoom(
            studyRoomTime: Any?,
        ) : UiEvent() // todo implement
    }

    override val _uiState = MutableStateFlow(StudyRoomListUiState())

    override fun onEvent(
        event: UiEvent,
    ) {
        when (event) {
            is UiEvent.FetchStudyRooms -> {
                fetchStudyRoomList(
                    timeSlot = event.timeSlot,
                )
            }
            is UiEvent.FetchStudyRoomAvailableTimes -> {
                fetchStudyRoomAvailableTimeList()
            }
            is UiEvent.FilterStudyRoom -> {
                // todo
            }
        }
    }

    private fun fetchApplyTime() {
        viewModelScope.launch {

            val result = kotlin.runCatching {
                studyRoomApplyTimeUseCase()
            }

            if (result.isSuccess) {

                val resultEntity = result.getOrThrow()

                _uiState.value = _uiState.value.copy(
                    startAt = resultEntity.startAt.extractHourFromDate(),
                    endAt = resultEntity.endAt.extractHourFromDate(),
                    hasApplyTime = true,
                )
            } else {
                val exception = result.exceptionOrNull()

                if (exception !is team.aliens.dms.android.domain._legacy.exception.RemoteException.NotFound) {

                    emitErrorEventFromThrowable(exception)
                }
            }
        }
    }

    private fun fetchStudyRoomList(
        timeSlot: UUID,
    ) {
        viewModelScope.launch {

            val result = kotlin.runCatching {
                fetchStudyRoomsUseCase(
                    fetchStudyRoomsInput = FetchStudyRoomsInput(
                        timeSlot = timeSlot,
                    ),
                )
            }

            if (result.isSuccess) {

                val resultEntity = result.getOrThrow()

                _uiState.value = _uiState.value.copy(
                    studyRooms = resultEntity.studyRooms.toInformation(),
                )
            } else {
                if (result.exceptionOrNull() !is NullPointerException) {
                    emitErrorEventFromThrowable(result.exceptionOrNull())
                }
            }
        }
    }

    private fun fetchStudyRoomAvailableTimeList() {
        viewModelScope.launch {
            kotlin.runCatching {
                studyRoomAvailableTimeListUseCase()
            }.onSuccess { resultEntity ->
                _uiState.value = _uiState.value.copy(
                    studyRoomAvailableTime = resultEntity.timeSlots,
                )
            }.onFailure {
                emitErrorEventFromThrowable(
                    throwable = it
                )
            }
        }
    }
}

data class StudyRoomListUiState(
    var startAt: String = "",
    var endAt: String = "",
    var studyRooms: List<StudyRoomInformation> = emptyList(),
    var studyRoomAvailableTime: List<FetchAvailableStudyRoomTimesOutput.TimeSlotInformation> = emptyList(),
    var timeSlot: UUID? = null,
    var hasApplyTime: Boolean = false,
) : UiState

data class StudyRoomInformation(
    val roomId: UUID,
    val position: String,
    val title: String,
    val currentNumber: Int,
    val isMine: Boolean,
    val maxNumber: Int,
    val condition: String,
)

internal fun FetchStudyRoomsOutput.StudyRoomInformation?.toInformation(): StudyRoomInformation {
    requireNotNull(this)

    val grade = if (availableGrade == 0) "모든" else availableGrade

    return StudyRoomInformation(
        roomId = id,
        position = "${floor}층",
        title = name,
        currentNumber = inUseHeadcount,
        isMine = isMine,
        maxNumber = totalAvailableSeat,
        condition = "${grade}학년 ${availableSex.koreanValue}",
    )
}

internal fun List<FetchStudyRoomsOutput.StudyRoomInformation?>.toInformation(): List<StudyRoomInformation> {
    return if (this.isNotEmpty()) {
        this.map { it.toInformation() }
    } else {
        return emptyList()
    }
}
