package team.aliens.dms.android.core.notification

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat

fun notificationPermissionGranted(context: Context): Boolean {
    return (
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS,
        ) == PackageManager.PERMISSION_GRANTED
        ) || Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU
}
