package team.aliens.dms_android.util.manager

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// TODO Dialog 관리 로직 추가

internal data class DialogState(
    val content: String,
    val mainBtnText: String,
    val subBtnText: String?,
    val onMainBtnClick: () -> Unit,
    val onSubBtnClick: (() -> Unit)?,
)

internal object DialogManager{
    private val _dialogState: MutableStateFlow<List<DialogState>> = MutableStateFlow(emptyList())
    val dialogState: StateFlow<List<DialogState>> = _dialogState.asStateFlow()

    fun setDialogState(
        content: String,
        mainBtnText: String,
        subBtnText: String? = null,
        onMainBtnClick: () -> Unit,
        onSubBtnClick: (() -> Unit)? = null,
    ){
        _dialogState.update { dialogStates ->
            dialogStates + DialogState(
                content = content,
                mainBtnText = mainBtnText,
                subBtnText = subBtnText,
                onMainBtnClick = onMainBtnClick,
                onSubBtnClick = onSubBtnClick,
            )
        }
    }
}

internal enum class DialogType{
    SINGLE, DOUblE
}