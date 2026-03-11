package team.aliens.dms.android.app

import android.graphics.Color
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge

fun ComponentActivity.setEdgeToEdgeConfig() {
    enableEdgeToEdge(
        statusBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT),
        navigationBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT),
    )
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        // Force the 3-button navigation bar to be transparent
        // See: https://developer.android.com/develop/ui/views/layout/edge-to-edge#create-transparent
        window.isNavigationBarContrastEnforced = false
    }
}
