package team.aliens.dms_android.common

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import team.aliens.design_system.toast.ToastType

val LocalAvailableFeatures = staticCompositionLocalOf {
    emptyMap<String, Boolean>()
}

val LocalToast = compositionLocalOf {
    { _: String, _: ToastType -> Unit }
}