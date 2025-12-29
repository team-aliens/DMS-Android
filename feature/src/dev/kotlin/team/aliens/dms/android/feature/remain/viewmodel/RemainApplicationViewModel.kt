package team.aliens.dms.android.feature.remain.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.android.core.ui.viewmodel.BaseStateViewModel
import team.aliens.dms.android.data.remain.model.RemainsApplicationTime
import team.aliens.dms.android.data.remain.model.RemainsOption
import team.aliens.dms.android.data.remain.repository.RemainsRepository
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class RemainApplicationViewModel @Inject constructor(
   val remainsRepository: RemainsRepository
): BaseStateViewModel<RemainApplicationState, Unit>(RemainApplicationState()) {

    init {
        getRemainsOptions()
        getRemainsApplicationTime()
    }

    private fun getRemainsOptions() {
        viewModelScope.launch {
            remainsRepository.fetchRemainsOptions().onSuccess { remainsOptions ->
                val selectRemainsOptionId =
                    remainsOptions.find { it.applied }?.id ?: UUID.randomUUID()
                setState {
                    it.copy(
                        remainsOptions = remainsOptions,
                        selectRemainsOptionId = selectRemainsOptionId,
                    )
                }
            }
        }
    }

    private fun getRemainsApplicationTime() {
        viewModelScope.launch {
            remainsRepository.fetchRemainsApplicationTime().onSuccess { time ->
                setState { it.copy(remainsApplicationTime = time) }
            }
        }
    }

    internal fun setSelectRemainsOption(remainsOptionId: UUID?) {
        setState { it.copy(selectRemainsOptionId = remainsOptionId) }
    }

    internal fun changeRemainsOption(
        onShowSnackBar: (DmsSnackBarType, String) -> Unit,
    ) {
        viewModelScope.launch {
            if (!isWithinApplicationTime()) {
                onShowSnackBar(DmsSnackBarType.ERROR, "잔류 신청 시간이 아닙니다")
                return@launch
            }
            uiState.value.selectRemainsOptionId?.let { optionId ->
                remainsRepository.updateRemainsOption(optionId = optionId)
                    .onSuccess {
                        val remainsOptions = uiState.value.remainsOptions.map { remainsOption ->
                            remainsOption.copy(applied = remainsOption.id == uiState.value.selectRemainsOptionId)
                        }
                        val appliedOption = remainsOptions.find { it.applied }
                        setState {
                            it.copy(
                                remainsOptions = remainsOptions,
                                selectedRemainTitle = appliedOption?.title
                            )
                        }
                        onShowSnackBar(DmsSnackBarType.SUCCESS, "잔류 신청이 완료되었습니다")
                    }.onFailure {
                        onShowSnackBar(DmsSnackBarType.ERROR, "잔류 신청에 실패했습니다")
                    }
                }
            }
    }

    private fun isWithinApplicationTime(): Boolean {
        val now = LocalDateTime.now()
        val currentDayOfWeek = now.dayOfWeek.value
        val currentTime = now.toLocalTime()

        val applicationTime = uiState.value.remainsApplicationTime

        if (applicationTime.startTime.isEmpty() || applicationTime.endTime.isEmpty()) {
            return true
        }

        val startTime = LocalTime.parse(applicationTime.startTime)
        val endTime = LocalTime.parse(applicationTime.endTime)
        val startDayValue = applicationTime.startDayOfWeek.value
        val endDayValue = applicationTime.endDayOfWeek.value

        if (startDayValue == endDayValue) {
            return currentDayOfWeek == startDayValue &&
                    currentTime >= startTime && currentTime <= endTime
        }

        return when (currentDayOfWeek) {
            startDayValue -> currentTime >= startTime
            endDayValue -> currentTime <= endTime
            in (startDayValue + 1)..<endDayValue -> true
            else -> false
        }
    }
}

data class RemainApplicationState(
    val remainsOptions: List<RemainsOption> = emptyList(),
    val selectRemainsOptionId: UUID? = null,
    val selectedRemainTitle: String? = null,
    val remainsApplicationTime: RemainsApplicationTime = RemainsApplicationTime(),
)
