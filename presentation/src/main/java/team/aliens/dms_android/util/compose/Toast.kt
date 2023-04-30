package team.aliens.dms_android.util.compose

import androidx.compose.runtime.Composable
import team.aliens.design_system.toast.ToastType
import team.aliens.dms_android.common.LocalToast

@Composable
fun ShowToast(
    message: String,
    toastType: ToastType,
) {
    if(message.isNotEmpty()) {
        LocalToast.current(
            message, toastType,
        )
    }
}