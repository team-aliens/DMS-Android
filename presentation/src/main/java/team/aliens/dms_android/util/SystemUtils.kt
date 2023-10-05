package team.aliens.dms_android.util

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings


@SuppressLint("HardwareIds")
internal fun Context.getDeviceUid(): String {
    val uid = Settings.Secure.getString(
        contentResolver,
        Settings.Secure.ANDROID_ID,
    )
    requireNotNull(uid)
    return uid
}