package team.aliens.dms_android.feature._legacy.handler

import android.app.Application
import android.content.Context
import android.util.Log
import team.aliens.dms_android.feature._legacy.DmsAppState
import javax.inject.Inject
import kotlin.system.exitProcess

internal class DmsExceptionHandler @Inject constructor(
    private val context: Context,
) : Thread.UncaughtExceptionHandler {
    override fun uncaughtException(
        thread: Thread,
        exception: Throwable,
    ) {
        // 디버그용 로그
        Log.w(
            thread.name,
            "uncaughtException: ${exception.message}\n" + exception.printStackTrace(),
        )
        exitProcess(-1)
    }

    companion object {
        private var appState: DmsAppState? = null
        fun setAppState(appState: DmsAppState) {
            if (Companion.appState == null) Companion.appState = appState
        }
    }
}

internal fun Application.initExceptionHandler() {
    Thread.setDefaultUncaughtExceptionHandler(
        DmsExceptionHandler(
            context = applicationContext,
        ),
    )
}
