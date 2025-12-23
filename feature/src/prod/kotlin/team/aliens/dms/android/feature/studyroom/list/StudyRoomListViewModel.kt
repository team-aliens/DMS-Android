package team.aliens.dms.android.feature.studyroom.list

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.ui.mvi.BaseMviViewModel
import team.aliens.dms.android.core.ui.mvi.Intent
import team.aliens.dms.android.core.ui.mvi.SideEffect
import team.aliens.dms.android.core.ui.mvi.UiState
import team.aliens.dms.android.data.studyroom.model.AvailableStudyRoomTime
import team.aliens.dms.android.data.studyroom.model.StudyRoom
import team.aliens.dms.android.data.studyroom.model.StudyRoomApplicationTime
import team.aliens.dms.android.data.studyroom.repository.StudyRoomRepository
import javax.inject.Inject

@HiltViewModel
internal class StudyRoomListViewModel @Inject constructor(
    private val studyRoomRepository: StudyRoomRepository,
) : BaseMviViewModel<StudyRoomListUiState, StudyRoomListIntent, StudyRoomListSideEffect>(
    initialState = StudyRoomListUiState.initial(),
) {
    init {
        fetchStudyRoomApplicationTime()
        initStudyRooms()
    }

    override fun processIntent(intent: StudyRoomListIntent) {
        when (intent) {
            is StudyRoomListIntent.UpdateSelectedAvailableStudyRoomTime -> updateSelectedAvailableStudyRoomTime(
                availableStudyRoomTime = intent.availableStudyRoomTime,
            )
        }
    }

    private fun fetchStudyRoomApplicationTime() = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            studyRoomRepository.fetchStudyRoomApplicationTime()
        }.onSuccess { fetchedStudyRoomApplicationTime ->
            reduce(
                newState = stateFlow.value.copy(
                    studyRoomApplicationTime = fetchedStudyRoomApplicationTime,
                ),
            )
        }
    }

    // TODO 분리
    private fun initStudyRooms() = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            studyRoomRepository.fetchAvailableStudyRoomTimes()
        }.onSuccess { fetchedAvailableStudyRoomTimes ->
            if (fetchedAvailableStudyRoomTimes.isNotEmpty()) {
                val firstOfAvailableStudyRoomTime = fetchedAvailableStudyRoomTimes.firstOrNull()
                val studyRooms =
                    firstOfAvailableStudyRoomTime?.let { studyRoomRepository.fetchStudyRooms(it.id) }
                reduce(
                    newState = stateFlow.value.copy(
                        availableStudyRoomTimes = fetchedAvailableStudyRoomTimes,
                        selectedAvailableStudyRoomTime = firstOfAvailableStudyRoomTime,
                        studyRooms = studyRooms,
                    ),
                )
            }
        }
    }

    private fun updateSelectedAvailableStudyRoomTime(
        availableStudyRoomTime: AvailableStudyRoomTime,
    ) = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            studyRoomRepository.fetchStudyRooms(timeslot = availableStudyRoomTime.id)
        }.onSuccess { fetchedStudyRooms ->
            reduce(
                newState = stateFlow.value.copy(
                    studyRooms = fetchedStudyRooms,
                    selectedAvailableStudyRoomTime = availableStudyRoomTime,
                ),
            )
        }
    }
}

internal data class StudyRoomListUiState(
    val studyRoomApplicationTime: StudyRoomApplicationTime?,
    val availableStudyRoomTimes: List<AvailableStudyRoomTime>?,
    val selectedAvailableStudyRoomTime: AvailableStudyRoomTime?,
    val studyRooms: List<StudyRoom>?,
) : UiState() {
    companion object {
        fun initial() = StudyRoomListUiState(
            studyRoomApplicationTime = null,
            availableStudyRoomTimes = null,
            selectedAvailableStudyRoomTime = null,
            studyRooms = null,
        )
    }
}

internal sealed class StudyRoomListIntent : Intent() {
    class UpdateSelectedAvailableStudyRoomTime(val availableStudyRoomTime: AvailableStudyRoomTime) :
        StudyRoomListIntent()
}

internal sealed class StudyRoomListSideEffect : SideEffect()
