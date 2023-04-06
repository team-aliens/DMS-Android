package team.aliens.dms_android.common

import androidx.compose.runtime.staticCompositionLocalOf

val LocalAvailableFeatures = staticCompositionLocalOf {
    mutableMapOf<String, Boolean>()
}