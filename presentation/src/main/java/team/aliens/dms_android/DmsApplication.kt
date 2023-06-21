package team.aliens.dms_android

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import team.aliens.dms_android.handler.initExceptionHandler

@HiltAndroidApp
class DmsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initExceptionHandler()
    }
}
