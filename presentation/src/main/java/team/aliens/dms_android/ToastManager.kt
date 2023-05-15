package team.aliens.dms_android

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import team.aliens.design_system.toast.ToastType

data class ToastMessage(
    val message: String = "",
    val toastType: ToastType = ToastType.SUCCESS,
)

object ToastManager {
    private val _message: MutableStateFlow<ToastMessage> = MutableStateFlow(ToastMessage())
    val message: StateFlow<ToastMessage> = _message.asStateFlow()

    fun setMessage(
        message: String,
        toastType: ToastType,
    ) {
        _message.update {
            ToastMessage(
                message = message,
                toastType = toastType,
            )
        }
    }
}