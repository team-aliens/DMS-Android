package team.aliens.dms.android.feature.remain.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import team.aliens.dms.android.core.ui.viewmodel.BaseStateViewModel
import team.aliens.dms.android.data.remain.model.RemainsApplicationTime
import team.aliens.dms.android.data.remain.model.RemainsOption
import team.aliens.dms.android.data.remain.repository.RemainsRepository
import java.util.UUID
import javax.inject.Inject
import kotlin.collections.map

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
                val selectRemainsOptionId = remainsOptions.find { it.applied }?.id ?: UUID.randomUUID()
                setState {
                    it.copy(
                        remainsOptions = remainsOptions,
                        selectRemainsOptionId = selectRemainsOptionId,
                    )
                }
            }
        }
    }

    private fun getRemainsApplicationTime() { // TODO :: 2일 이상인 경우 시간이 아닌 날짜로 선택할 수 있도록 구현
        viewModelScope.launch {
            remainsRepository.fetchRemainsApplicationTime().onSuccess { time ->
                setState { it.copy(remainsApplicationTime = time) }
            }
        }
    }

    internal fun setSelectRemainsOption(remainsOptionId: UUID) {
        setState { it.copy(selectRemainsOptionId = remainsOptionId) }
    }

    internal fun changeRemainsOption() {
        viewModelScope.launch {
            val remainOptionId = uiState.value.selectRemainsOptionId
            remainsRepository.updateRemainsOption(optionId = remainOptionId ?: UUID.randomUUID()).onSuccess {
                val remainsOptions = uiState.value.remainsOptions.map { remainsOption ->
                    if (remainsOption.id == uiState.value.selectRemainsOptionId) {
                        setState { it.copy(selectedRemainTitle = remainsOption.title) }
                        remainsOption.copy(applied = true)
                    } else {
                        remainsOption.copy(applied = false)
                    }
                }
                setState { it.copy(remainsOptions = remainsOptions) }
            }
        }
    }
}

data class RemainApplicationState(
    val remainsOptions: List<RemainsOption> = emptyList(),
    val selectRemainsOptionId: UUID? = null,
    val selectedRemainTitle: String = "금요 귀가",
    val remainsApplicationTime: RemainsApplicationTime = RemainsApplicationTime(),
)
