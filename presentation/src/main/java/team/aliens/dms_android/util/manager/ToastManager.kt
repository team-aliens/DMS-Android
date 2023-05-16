package team.aliens.dms_android.util.manager

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import team.aliens.design_system.toast.ToastType

internal data class ToastMessage(
    val message: String,
    val toastType: ToastType,
)

internal object ToastManager {
    private val _message: MutableStateFlow<List<ToastMessage>> = MutableStateFlow(emptyList())
    val message: StateFlow<List<ToastMessage>> = _message.asStateFlow()

    fun setMessage(
        message: String,
        toastType: ToastType,
    ) {
        _message.update { toastMessage ->
            toastMessage + ToastMessage(
                message = message,
                toastType = toastType,
            )
        }
    }
}