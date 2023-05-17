package team.aliens.dms_android.common

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import team.aliens.design_system.toast.ToastType

internal val LocalAvailableFeatures = staticCompositionLocalOf { mutableMapOf<String, Boolean>() }

val LocalToast = compositionLocalOf {
    { toastMessage: String, toastType: ToastType -> Unit }
}